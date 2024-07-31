package pruebamiguelmachin.beertapdispenser.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pruebamiguelmachin.beertapdispenser.application.mappers.DispenserMapper;
import pruebamiguelmachin.beertapdispenser.application.mappers.DispenserUsageMapper;
import pruebamiguelmachin.beertapdispenser.application.usercases.DispenserService;
import pruebamiguelmachin.beertapdispenser.domain.model.constants.DispenserConstants;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserSpendingDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserStatusDto;
import pruebamiguelmachin.beertapdispenser.domain.model.enums.DispenserStatusEnum;
import pruebamiguelmachin.beertapdispenser.domain.port.DispenserPersistencePort;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;

;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class DispenserServiceImpl implements DispenserService {

    private final DispenserMapper dispenserMapper;
    private final DispenserPersistencePort dispenserPersistencePort;
    private final DispenserUsageMapper dispenserUsageMapper;

    @Autowired
    public DispenserServiceImpl (DispenserPersistencePort dispenserPersistencePort,
                                 DispenserMapper dispenserMapper,
                                 DispenserUsageMapper dispenserUsageMapper) {
        this.dispenserPersistencePort = dispenserPersistencePort;
        this.dispenserMapper = dispenserMapper;
        this.dispenserUsageMapper = dispenserUsageMapper;
    }


    @Override
    public DispenserDto createDispenser(float flow_volume, float price_per_liter) {
        DispenserEntity dispenserEntity = dispenserPersistencePort.create(flow_volume, price_per_liter);

        return Stream.of(dispenserEntity).map(dispenserMapper.mapToDispenserDto).findFirst().get();

    }

    @Override
    public void updateStatus(String id, DispenserStatusDto dispenserStatusDto) {
        DispenserEntity dispenserEntity = dispenserPersistencePort.getById(UUID.fromString(id));

        if (dispenserEntity.getStatus() == dispenserStatusDto.getStatus()) {
            throw new DispenserException(HttpStatus.CONFLICT, String.format(DispenserConstants.DISPENSER_CONFLICT_MESSAGE_ERROR, dispenserEntity.getId()));
        }

        dispenserEntity.setStatus(dispenserStatusDto.getStatus());
        if (dispenserStatusDto.getStatus() == DispenserStatusEnum.OPEN.ordinal()) {
            DispenserUsageEntity usage = createUsage(dispenserEntity, dispenserStatusDto);

            if (dispenserEntity.getUsages() == null) {
                List<DispenserUsageEntity> usages = new ArrayList<>();
                usages.add(usage);
                dispenserEntity.setUsages(usages);
            } else {
                dispenserEntity.getUsages().add(usage);
            }

            dispenserPersistencePort.save(usage);
        }

        if (dispenserStatusDto.getStatus() == DispenserStatusEnum.CLOSE.ordinal()) {
            List<DispenserUsageEntity> usages = dispenserEntity.getUsages();
            if (!usages.isEmpty()) {
                DispenserUsageEntity usage = usages.stream().filter(dispenserUsageEntity -> dispenserUsageEntity.getCloseAt() == null).findFirst().orElse(null);
                if (usage == null) {
                    throw new DispenserException(HttpStatus.NOT_FOUND, String.format(DispenserConstants.DISPENSER_NOT_FOUND_USAGE_MESSAGE_ERROR, dispenserEntity.getId()));
                }

                usage.setCloseAt(dispenserStatusDto.getUpdated_at());
                usage.setTotalSpent(
                        calculateTotalSpend(dispenserEntity.getPrice_per_liter(), dispenserEntity.getFlow_volumen(), usage.getOpenetAt(), usage.getCloseAt())
                );

                dispenserPersistencePort.save(usage);
            } else {
                throw new DispenserException(HttpStatus.NOT_FOUND, String.format(DispenserConstants.DISPENSER_NOT_FOUND_USAGE_MESSAGE_ERROR, dispenserEntity.getId()));
            }

        }

        dispenserPersistencePort.updateStatus(dispenserEntity);
    }

    @Override
    public DispenserDto findDispenser(String id) {
        return Stream.of(dispenserPersistencePort.getById(UUID.fromString(id))).map(dispenserMapper.mapToDispenserDto).findFirst().get();
    }

    @Override
    public DispenserSpendingDto findDispenserSpending(String id) throws DispenserException  {
        DispenserEntity dispenserEntity = dispenserPersistencePort.getById(UUID.fromString(id));
        float amount = 0.0f;

        if (dispenserEntity.getUsages() != null ){
            for (DispenserUsageEntity usageEntity : dispenserEntity.getUsages()) {
                amount+=usageEntity.getTotalSpent();
            }
        }

        DispenserSpendingDto dispenserSpendingDto = new DispenserSpendingDto();
        dispenserSpendingDto.setAmount(amount);
        dispenserSpendingDto.setUsages(
                dispenserEntity.getUsages() != null ? new ArrayList<>(Stream.of(dispenserEntity.getUsages()).map(dispenserUsageMapper.mapToListUsageDto).findFirst().get()) : null
        );

        return dispenserSpendingDto;

    }

    private DispenserUsageEntity createUsage(DispenserEntity dispenserEntity, DispenserStatusDto dispenserStatusDto) {
        DispenserUsageEntity dispenserUsageEntity = new DispenserUsageEntity();
        dispenserUsageEntity.setDispenser(dispenserEntity);
        dispenserUsageEntity.setOpenetAt(dispenserStatusDto.getUpdated_at());
        return dispenserUsageEntity;
    }

    private float calculateTotalSpend(float price_per_litter, float flow_volumen, Date openetAt, Date closeAt) {
        long seconds = (closeAt.getTime() - openetAt.getTime())/1000;
        return (seconds * flow_volumen) * price_per_litter;
    }

}

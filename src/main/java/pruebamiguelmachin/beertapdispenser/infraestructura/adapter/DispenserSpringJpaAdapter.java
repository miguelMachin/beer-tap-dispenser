package pruebamiguelmachin.beertapdispenser.infraestructura.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pruebamiguelmachin.beertapdispenser.domain.model.constants.DispenserConstants;
import pruebamiguelmachin.beertapdispenser.domain.model.enums.DispenserStatusEnum;
import pruebamiguelmachin.beertapdispenser.domain.port.DispenserPersistencePort;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.repositories.JpaDispenserRepository;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.repositories.JpaDispenserUsageRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class DispenserSpringJpaAdapter implements DispenserPersistencePort {

    private final JpaDispenserRepository jpaDispenserRepository;
    private final JpaDispenserUsageRepository jpaDispenserUsageRepository;

    @Autowired
    public DispenserSpringJpaAdapter(JpaDispenserRepository jpaDispenserRepository, JpaDispenserUsageRepository jpaDispenserUsageRepository) {
        this.jpaDispenserRepository = jpaDispenserRepository;
        this.jpaDispenserUsageRepository = jpaDispenserUsageRepository;
    }

    @Override
    public DispenserEntity create(float flow_volume, float price_per_liter) {
        DispenserEntity dispenserEntity = new DispenserEntity();
        dispenserEntity.setId(UUID.randomUUID());
        dispenserEntity.setFlow_volumen(flow_volume);
        dispenserEntity.setPrice_per_liter(price_per_liter);
        dispenserEntity.setStatus(DispenserStatusEnum.CLOSE.ordinal());
        //dispenserEntity.setUsages(new ArrayList<>());
        return jpaDispenserRepository.save(dispenserEntity);
    }

    @Override
    public void updateStatus(DispenserEntity dispenserEntity) {
        jpaDispenserRepository.save(dispenserEntity);
    }

    @Override
    public DispenserEntity getById(UUID id) throws DispenserException {
        Optional<DispenserEntity> dispenserEntityOptional = jpaDispenserRepository.findById(id);

        if (!dispenserEntityOptional.isPresent()){
            throw new DispenserException(HttpStatus.NOT_FOUND, String.format(DispenserConstants.DISPENSER_NOT_FOUND_MESSAGE_ERROR, id));
        }

        return dispenserEntityOptional.get();
    }

    @Override
    public void save(DispenserUsageEntity dispenserUsageEntity) {
        jpaDispenserUsageRepository.save(dispenserUsageEntity);
    }


}

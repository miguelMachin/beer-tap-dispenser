package pruebamiguelmachin.beertapdispenser.application.mappers;

import org.springframework.stereotype.Component;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.UsagesDto;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DispenserUsageMapper {
    public Function<DispenserUsageEntity, UsagesDto> mapToUsageDto = p -> {
        UsagesDto usagesDto = new UsagesDto();
        usagesDto.setId(p.getId());
        usagesDto.setOpenet_at(p.getOpenetAt());
        usagesDto.setClose_at(p.getCloseAt() != null ? p.getCloseAt() : null);
        usagesDto.setTotal_spent(p.getTotalSpent());
        return usagesDto;
    };

    public Function<UsagesDto, DispenserUsageEntity> mapToUsage = p -> {
        DispenserUsageEntity dispenserUsage = new DispenserUsageEntity();
        if(p.getId() != null) {
            dispenserUsage.setId(p.getId());
        }
        dispenserUsage.setOpenetAt(p.getOpenet_at());
        dispenserUsage.setCloseAt(p.getClose_at() != null ? p.getClose_at() : null);
        dispenserUsage.setTotalSpent(p.getTotal_spent());
        return dispenserUsage;
    };


    public Function<List<DispenserUsageEntity>, List<UsagesDto>> mapToListUsageDto = l -> l.stream()
            .map(mapToUsageDto).collect(Collectors.toList());

    public Function<List<UsagesDto>, List<DispenserUsageEntity>> mapToListUsage = l -> l.stream()
            .map(mapToUsage).collect(Collectors.toList());


}


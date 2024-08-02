package pruebamiguelmachin.beertapdispenser.application.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.UsagesDto;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DispenserMapper {

    @Autowired
    DispenserUsageMapper dispenserUsageMapper;

    public Function<DispenserEntity, DispenserDto> mapToDispenserDto = p -> {
        DispenserDto dispenserDto = new DispenserDto();
        dispenserDto.setId(p.getId().toString());
        dispenserDto.setFlow_volume(p.getFlow_volumen());
        dispenserDto.setPrice_per_liter(p.getPrice_per_liter());
        dispenserDto.setUsages(p.getUsages() != null ? new ArrayList<>(Stream.of(p.getUsages()).map(dispenserUsageMapper.mapToListUsageDto).findFirst().get()) : null);
        return dispenserDto;
    };


    public Function<DispenserDto, DispenserEntity> mapToDispenserEntity = p -> {
        DispenserEntity dispenserEntity = new DispenserEntity();
        /*if (p.getId() != null) {
            dispenserEntity.setId(p.getId());
        }*/
        dispenserEntity.setPrice_per_liter(p.getPrice_per_liter());
        dispenserEntity.setStatus(p.getStatus());
        dispenserEntity.setFlow_volumen(p.getFlow_volume());
       // dispenserEntity.setUsages(new ArrayList<>(Stream.of(p.getUsages()).map(dispenserUsageMapper.mapToListUsage).findFirst().get()));
        return dispenserEntity;
    };



    public Function<List<DispenserEntity>, List<DispenserDto>> mapToListDispenserDto = l -> l.stream()
            .map(mapToDispenserDto).collect(Collectors.toList());


    public Function<List<DispenserDto>, List<DispenserEntity>> mapToListDispenserEntity = l -> l.stream()
            .map(mapToDispenserEntity).collect(Collectors.toList());

}

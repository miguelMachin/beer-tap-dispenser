package pruebamiguelmachin.beertapdispenser.application.usercases;

import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserSpendingDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserStatusDto;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;

import java.util.List;
import java.util.UUID;

public interface DispenserService {
    DispenserDto createDispenser(float flow_volume, float price_per_liter);
    void updateStatus(String id, DispenserStatusDto dispenserStatusDto);
    DispenserDto findDispenser(String id);
    DispenserSpendingDto findDispenserSpending(String id) throws DispenserException;
    List<DispenserDto> findAll();

}

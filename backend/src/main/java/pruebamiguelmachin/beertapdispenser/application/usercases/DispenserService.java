package pruebamiguelmachin.beertapdispenser.application.usercases;

import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserSpendingDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserStatusDto;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;

import java.text.ParseException;
import java.util.List;

public interface DispenserService {
    DispenserDto createDispenser(float flow_volume, float price_per_liter);
    void updateStatus(String id, DispenserStatusDto dispenserStatusDto) throws ParseException;
    DispenserDto findDispenser(String id);
    DispenserSpendingDto findDispenserSpending(String id) throws DispenserException;
    List<DispenserDto> findAll();

}

package pruebamiguelmachin.beertapdispenser.infraestructura.rest.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pruebamiguelmachin.beertapdispenser.application.usercases.DispenserService;
import pruebamiguelmachin.beertapdispenser.domain.model.constants.DispenserConstants;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserRequestDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserSpendingDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserStatusDto;
import pruebamiguelmachin.beertapdispenser.domain.model.enums.DispenserStatusEnum;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;


import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/dispenser")
public class DispenserController {

    private final DispenserService dispenserService;

    public DispenserController(DispenserService dispenserService) {
        this.dispenserService = dispenserService;
    }


    @GetMapping
    public ResponseEntity getAllDispenser() {
        List<DispenserDto> dispensersDto = dispenserService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(dispensersDto);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody DispenserRequestDto dispenserRequestDto) {
        DispenserDto dispenser = dispenserService.createDispenser(dispenserRequestDto.getFlow_volume(), dispenserRequestDto.getPrice_per_liter());

        return new ResponseEntity<>(dispenser, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DispenserStatusDto> updateStatus(@PathVariable String id, @RequestBody DispenserStatusDto dispenserStatusDto) {

        try{
            dispenserService.updateStatus(id, dispenserStatusDto);
        } catch (DispenserException ex) {
            throw new ResponseStatusException(ex.getErrorCode(), ex.getErrorMessage());
        }

        return new ResponseEntity<>(dispenserStatusDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/spending")
    public ResponseEntity<DispenserSpendingDto> spendingById(@PathVariable String id) {
        DispenserSpendingDto dispenserSpendingDto = null;

        try {
            dispenserSpendingDto = dispenserService.findDispenserSpending(id);
        } catch (DispenserException ex) {
            throw new ResponseStatusException(ex.getErrorCode(), ex.getErrorMessage());
        }

        return new ResponseEntity<>(dispenserSpendingDto, HttpStatus.OK);
    }

}

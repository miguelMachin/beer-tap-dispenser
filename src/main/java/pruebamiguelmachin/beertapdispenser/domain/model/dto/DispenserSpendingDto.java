package pruebamiguelmachin.beertapdispenser.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DispenserSpendingDto {
    private float amount;
    private ArrayList<UsagesDto> usages;

}

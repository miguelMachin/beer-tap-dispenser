package pruebamiguelmachin.beertapdispenser.domain.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DispenserDto {
    private String id;
    private float flow_volume;
    private float amount;
    private ArrayList<UsagesDto> usages;
    private float price_per_liter;
    private int status;

    public DispenserDto() {

    }

}

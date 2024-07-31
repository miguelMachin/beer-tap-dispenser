package pruebamiguelmachin.beertapdispenser.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispenserRequestDto {
    private float flow_volume;
    private float price_per_liter;
}

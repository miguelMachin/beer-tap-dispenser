package pruebamiguelmachin.beertapdispenser.domain.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DispenserStatusDto {
    private int status;
    private Date updated_at;

}

package pruebamiguelmachin.beertapdispenser.domain.model.dto;



import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsagesDto {
    private Integer id;
    private String openet_at;
    private String close_at;
    private float flow_volume;
    private float total_spent;


}

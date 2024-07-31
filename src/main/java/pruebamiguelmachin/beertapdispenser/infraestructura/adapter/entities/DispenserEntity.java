package pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "dispenser")
public class DispenserEntity {
    @Id

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "flow_volumen")
    private float flow_volumen;

    @Column(name = "price_per_liter")
    private float price_per_liter;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "dispenser" , fetch=FetchType.EAGER)
    private List<DispenserUsageEntity> usages;


    public DispenserEntity() {

    }
}

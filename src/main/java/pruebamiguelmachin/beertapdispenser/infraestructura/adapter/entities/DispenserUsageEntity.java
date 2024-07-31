package pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "dispenserusages")
public class DispenserUsageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "openet_at")
    private Date openetAt;

    @Column(name = "close_at")
    private Date closeAt;
    @Column(name = "total_spent")
    private float totalSpent;

    @ManyToOne
    @JoinColumn(name= "dispenser_id")
    private DispenserEntity dispenser;

    public DispenserUsageEntity() {

    }
}
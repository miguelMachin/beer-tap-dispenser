package pruebamiguelmachin.beertapdispenser.domain.port;

import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;

import java.util.UUID;


public interface DispenserPersistencePort {
    void updateStatus(DispenserEntity dispenserEntity);

    DispenserEntity getById(UUID id) throws DispenserException;
    DispenserEntity create(float flow_volume, float price_per_liter);
    void save(DispenserUsageEntity dispenserUsageEntity);
}

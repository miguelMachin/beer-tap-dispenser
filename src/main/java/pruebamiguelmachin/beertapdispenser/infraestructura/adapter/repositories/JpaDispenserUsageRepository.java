package pruebamiguelmachin.beertapdispenser.infraestructura.adapter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;

public interface JpaDispenserUsageRepository extends JpaRepository<DispenserUsageEntity, Integer> {
}


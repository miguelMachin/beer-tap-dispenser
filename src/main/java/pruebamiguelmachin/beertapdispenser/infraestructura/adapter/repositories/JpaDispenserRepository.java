package pruebamiguelmachin.beertapdispenser.infraestructura.adapter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDispenserRepository extends JpaRepository<DispenserEntity, String> {
    Optional<DispenserEntity> findById(UUID id);

}

package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.PremisesEntity;

import java.util.Optional;

@Repository
public interface PremisesRepository extends JpaRepository<PremisesEntity, Integer> {
    Optional<PremisesEntity> findById(int id);
}

package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.ChargeTypeEntity;

@Repository
public interface ChargeTypeRepository extends JpaRepository<ChargeTypeEntity, Integer> {
}

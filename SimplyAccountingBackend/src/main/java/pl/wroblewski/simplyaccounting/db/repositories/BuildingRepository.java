package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.BuildingEntity;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {
    List<BuildingEntity> findAllByCooperative_IdOrderByHouseNumber(Integer cooperativeId);
}

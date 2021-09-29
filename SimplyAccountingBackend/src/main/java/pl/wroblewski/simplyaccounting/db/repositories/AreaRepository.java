package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.AreaEntity;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Integer> {
    List<AreaEntity> findAllByPremises_IdAndAreaType_IdOrderByStartDate(Integer premisesId, Integer areaTypeId);

    List<AreaEntity> findAllByPremises_IdOrderByStartDate(Integer premisesId);
}

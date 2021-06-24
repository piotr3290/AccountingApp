package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.PeopleNumberEntity;

import java.util.List;

@Repository
public interface PeopleNumberRepository extends JpaRepository<PeopleNumberEntity, Integer> {
    List<PeopleNumberEntity> findAllByPremises_IdOrderByStartDate(Integer premisesId);
}

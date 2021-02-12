package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.CooperativeEntity;


@Repository
public interface CooperativeRepository extends JpaRepository<CooperativeEntity, Integer> {
}

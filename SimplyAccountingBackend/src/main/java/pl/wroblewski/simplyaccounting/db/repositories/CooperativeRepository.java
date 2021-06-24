package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.CooperativeEntity;

import java.util.Optional;

@Repository
public interface CooperativeRepository extends JpaRepository<CooperativeEntity, Integer> {

    @Query("SELECT c FROM CooperativeEntity c " +
            "WHERE c.id = ( " +
            "SELECT b.cooperative.id FROM BuildingEntity b " +
            "WHERE b.id = ( " +
            "SELECT p.building.id FROM PremisesEntity p " +
            "WHERE p.id = :premisesId))")
    Optional<CooperativeEntity> findByPremises(
            @Param("premisesId") Integer premisesId
    );
}

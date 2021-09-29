package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.LandlordEntity;

import java.util.List;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordEntity, Integer> {

    @Query(value = "SELECT * FROM landlords l " +
            "WHERE l.id IN ( " +
            "SELECT pl.landlord_id FROM premises_landlords pl " +
            "WHERE pl.premises_id IN ( " +
            "SELECT p.id FROM premises p " +
            "WHERE p.building_id IN ( " +
            "SELECT b.id FROM buildings b " +
            "WHERE b.cooperative_id = :cooperativeId )))",
            nativeQuery = true)
    List<LandlordEntity> findAllLandlordsForCooperative(@Param("cooperativeId") Integer cooperativeID);

    @Query(value = "SELECT * FROM landlords " +
            "WHERE id IN ( " +
            "SELECT landlord_id FROM premises_landlords " +
            "WHERE premises_id = :premisesId)",
            nativeQuery = true)
    List<LandlordEntity> findAllLandlordsForPremises(@Param("premisesId") Integer premisesId);

}

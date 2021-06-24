package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.ChargeElementEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChargeElementRepository extends JpaRepository<ChargeElementEntity, Integer> {

    @Query("SELECT c FROM ChargeElementEntity c " +
            "WHERE (c.startDate IS NULL OR c.startDate <= :endDate) " +
            "AND (c.endDate IS NULL OR :startDate <= c.endDate) " +
            "AND c.cooperative.id = :cooperativeId")
    List<ChargeElementEntity> findAllByCooperative_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate);


    @Query("SELECT c FROM ChargeElementEntity c " +
            "WHERE (c.startDate IS NULL OR c.startDate <= :endDate) " +
            "AND (c.endDate IS NULL OR :startDate <= c.endDate) " +
            "AND c.cooperative.id = ( " +
            "SELECT b.cooperative.id " +
            "FROM BuildingEntity b " +
            "WHERE b.id = :buildingId)")
    List<ChargeElementEntity> findAllByBuilding_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
            @Param("buildingId") Integer buildingId,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate);


    @Query("SELECT c FROM ChargeElementEntity c " +
            "WHERE (c.startDate IS NULL OR c.startDate <= :endDate) " +
            "AND (c.endDate IS NULL OR :startDate <= c.endDate) " +
            "AND c.cooperative.id = ( " +
            "SELECT b.cooperative.id " +
            "FROM BuildingEntity b " +
            "WHERE b.id = ( " +
            "SELECT p.building.id " +
            "FROM PremisesEntity p " +
            "WHERE p.id = :premisesId))")
    List<ChargeElementEntity> findAllByPremises_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
            @Param("premisesId") Integer premisesId,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate);


    List<ChargeElementEntity> findAllByChargeType_IdAndCooperative_IdAndPremisesType_Id(
            Integer chargeTypeId, Integer cooperativeId, Integer premisesTypeId);

    List<ChargeElementEntity> findAllByCooperative_Id(Integer cooperativeId);
}

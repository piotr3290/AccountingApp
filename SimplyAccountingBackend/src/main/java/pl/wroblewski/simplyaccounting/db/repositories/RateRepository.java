package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.RateEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Integer> {
    List<RateEntity> findAllByChargeType_IdAndCooperative_IdAndBuilding_IdAndPremises_Id(
            Integer chargeTypeId, Integer cooperativeId, Integer buildingId, Integer premisesId);

    List<RateEntity> findAllByChargeType_Id(Integer chargeTypeId);

    @Query("SELECT r FROM RateEntity r " +
            "LEFT JOIN PremisesEntity p ON r.premises.id = p.id " +
            "LEFT JOIN BuildingEntity b ON r.building.id = b.id OR p.building.id = b.id "+
            "WHERE (r.startDate IS NULL OR r.startDate <= :endDate) " +
            "AND (r.endDate IS NULL OR :startDate <= r.endDate) " +
            "AND r.chargeType.id IN :chargeTypes " +
            "AND (r.cooperative IS NULL OR r.cooperative.id = :cooperativeId) " +
            "AND (b.id IS NULL OR b.cooperative.id = :cooperativeId) " +
            "AND (p.id IS NULL OR b.cooperative.id = :cooperativeId) ")
    List<RateEntity> findAllByChargeTypesAndCooperativeAndPeriod(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("chargeTypes") List<Integer> chargeTypes);

    @Query("SELECT r FROM RateEntity r " +
            "LEFT JOIN PremisesEntity p ON r.premises.id = p.id " +
            "LEFT JOIN BuildingEntity b ON r.building.id = b.id OR p.building.id = b.id "+
            "WHERE (r.startDate IS NULL OR r.startDate <= :endDate) " +
            "AND (r.endDate IS NULL OR :startDate <= r.endDate) " +
            "AND r.chargeType.id IN :chargeTypes " +
            "AND (r.cooperative IS NULL OR r.cooperative.id = b.cooperative.id) " +
            "AND (b.id IS NULL OR b.id = :buildingId) " +
            "AND (p.id IS NULL OR p.building.id = :buildingId) ")
    List<RateEntity> findAllByChargeTypesAndBuildingAndPeriod(
            @Param("buildingId") Integer buildingId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("chargeTypes") List<Integer> chargeTypes);


    @Query("SELECT r FROM RateEntity r " +
            "LEFT JOIN PremisesEntity p ON r.premises.id = p.id " +
            "LEFT JOIN BuildingEntity b ON r.building.id = b.id OR p.building.id = b.id "+
            "WHERE (r.startDate IS NULL OR r.startDate <= :endDate) " +
            "AND (r.endDate IS NULL OR :startDate <= r.endDate) " +
            "AND r.chargeType.id IN :chargeTypes " +
            "AND (r.cooperative IS NULL OR r.cooperative.id = b.cooperative.id) " +
            "AND (b.id IS NULL OR b.id = p.building.id) " +
            "AND (p.id IS NULL OR p.id = :premisesId) ")
    List<RateEntity> findAllByChargeTypesAndPremisesAndPeriod(
            @Param("premisesId") Integer premisesId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("chargeTypes") List<Integer> chargeTypes);
}

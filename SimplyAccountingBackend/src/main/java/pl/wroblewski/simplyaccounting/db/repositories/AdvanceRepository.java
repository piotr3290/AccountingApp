package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.AdvanceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvanceRepository extends JpaRepository<AdvanceEntity, Integer> {
    boolean existsByLandlord_IdAndPremises_IdAndChargeType_IdAndMonthAndYear(
            Integer landlordId, Integer premisesId, Integer chargeTypeId, Byte month, Short year);

    Optional<AdvanceEntity> findByLandlord_IdAndPremises_IdAndChargeType_IdAndMonthAndYear(
            Integer landlordId, Integer premisesId, Integer chargeTypeId, Byte month, Short year);

    List<AdvanceEntity> findAllByPremises_IdAndYear(Integer premisesId, Short year);

    @Query(value = "SELECT * FROM advances " +
            "WHERE year = :year " +
            "AND premises_id IN ( " +
            "SELECT id FROM premises " +
            "WHERE building_id IN ( " +
            "SELECT id FROM buildings " +
            "WHERE cooperative_id = :cooperativeId))",
            nativeQuery = true)
    List<AdvanceEntity> findAllByCooperativeAndYear(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("year") Short year);
}

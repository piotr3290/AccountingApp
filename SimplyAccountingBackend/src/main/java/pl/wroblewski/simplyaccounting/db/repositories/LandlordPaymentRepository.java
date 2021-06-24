package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.LandlordPaymentEntity;

import java.util.List;

@Repository
public interface LandlordPaymentRepository extends JpaRepository<LandlordPaymentEntity, Integer> {
    @Query(value = "SELECT * FROM landlords_payments " +
            "WHERE YEAR(realization_date) = :year " +
            "AND premises_id IN ( " +
            "SELECT id FROM premises " +
            "WHERE building_id IN ( " +
            "SELECT id FROM buildings " +
            "WHERE cooperative_id = :cooperativeId)) " +
            "ORDER BY realization_date",
            nativeQuery = true)
    List<LandlordPaymentEntity> findAllByYear(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("year") Short year);
}

package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.ContractorPaymentEntity;

import java.util.List;

@Repository
public interface ContractorPaymentRepository extends JpaRepository<ContractorPaymentEntity, Integer> {

    @Query(value = "SELECT * FROM contractors_payments " +
            "WHERE YEAR(realization_date) = :year AND cooperative_id = :cooperativeId " +
            "ORDER BY realization_date",
            nativeQuery = true)
    List<ContractorPaymentEntity> findAllByYear(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("year") Short year);
}

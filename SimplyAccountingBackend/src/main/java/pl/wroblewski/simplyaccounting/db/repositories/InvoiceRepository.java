package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.InvoiceEntity;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    List<InvoiceEntity> findAllByCooperative_IdOrderByRealizationDate(Integer cooperativeId);

    @Query(value = "SELECT * FROM invoices " +
            "WHERE cooperative_id = :cooperativeId " +
            "AND YEAR(realization_date) = :year",
            nativeQuery = true)
    List<InvoiceEntity> findAllByCooperativeAndYear(
            @Param("cooperativeId") Integer cooperativeId,
            @Param("year") Short year);
}

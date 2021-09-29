package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.InvoiceTypeEntity;

@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceTypeEntity, Integer> {
}

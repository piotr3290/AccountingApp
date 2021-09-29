package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.ContractorEntity;

@Repository
public interface ContractorRepository extends JpaRepository<ContractorEntity, Integer> {
}

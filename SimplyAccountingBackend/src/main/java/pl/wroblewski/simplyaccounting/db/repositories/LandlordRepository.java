package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.LandlordEntity;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordEntity, Integer> {
}

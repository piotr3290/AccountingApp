package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.OpeningBalanceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpeningBalanceRepository extends JpaRepository<OpeningBalanceEntity, Integer> {

    boolean existsByAccount_IdAndYearAndCooperative_Id(Integer accountId, Short year, Integer cooperativeId);

    Optional<OpeningBalanceEntity> findByAccount_IdAndYearAndCooperative_Id(Integer accountId, Short year,
                                                                            Integer cooperativeID);

    List<OpeningBalanceEntity> findAllByAccount_Cooperative_IdOrderByYear(int cooperativeId);

    List<OpeningBalanceEntity> findAllByAccount_Landlord_IdOrderByYear(int landlordId);

    List<OpeningBalanceEntity> findAllByAccount_Contractor_IdOrderByYear(int contractorId);

    List<OpeningBalanceEntity> findAllByAccount_IdOrderByYear(int accountId);
}

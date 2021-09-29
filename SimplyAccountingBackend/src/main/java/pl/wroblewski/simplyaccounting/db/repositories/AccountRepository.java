package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.AccountEntity;

import javax.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    @Transactional
    Integer deleteById(int id);

    @org.springframework.transaction.annotation.Transactional
    AccountEntity save(AccountEntity accountEntity);
}

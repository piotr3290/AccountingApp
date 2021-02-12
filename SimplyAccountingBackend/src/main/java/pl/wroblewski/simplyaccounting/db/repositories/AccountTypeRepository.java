package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.AccountTypeEntity;

import javax.transaction.Transactional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountTypeEntity, Integer> {

    @Transactional
    Integer deleteById(int id);

}

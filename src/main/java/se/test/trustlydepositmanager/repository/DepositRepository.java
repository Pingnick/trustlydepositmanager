package se.test.trustlydepositmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.test.trustlydepositmanager.model.Deposit;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {


}

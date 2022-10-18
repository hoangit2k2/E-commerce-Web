package rrs.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rrs.model.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	@Query("SELECT o FROM ACCOUNTS o WHERE o.email=:email")
	public Account getByEmail(String email);
}

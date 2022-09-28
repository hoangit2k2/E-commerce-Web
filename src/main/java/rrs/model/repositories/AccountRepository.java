package rrs.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rrs.model.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {}

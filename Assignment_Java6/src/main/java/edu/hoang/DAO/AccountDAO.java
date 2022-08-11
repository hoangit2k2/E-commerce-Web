package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Account;

public interface AccountDAO extends JpaRepository<Account,String> {
	
}

package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority,Integer> {
	
}

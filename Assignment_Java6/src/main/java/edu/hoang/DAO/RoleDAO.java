package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Role;
import edu.hoang.entity.Student;

public interface RoleDAO extends JpaRepository<Role,String> {
	
}

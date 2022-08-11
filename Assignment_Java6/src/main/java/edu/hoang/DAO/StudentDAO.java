package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Student;

public interface StudentDAO extends JpaRepository<Student,String> {
	
}

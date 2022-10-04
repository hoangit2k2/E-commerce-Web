package rrs.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rrs.model.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, String>{

}

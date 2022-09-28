package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rrs.model.entities.Staff;
import rrs.model.repositories.StaffRepository;
import rrs.model.utils.InterDAO;

@Service
public class StaffService implements InterDAO<Staff, String> {
	
	// @formatter:off
	
	@Autowired private StaffRepository rep;

	@Override // get entities and no conditional
	public List<Staff> getList() {
		return rep.findAll();
	}

	@Override // get entities and sort conditional
	public List<Staff> getList(Sort sort) {
		return rep.findAll(sort);
	}

	@Override // get page of the entities
	public Page<Staff> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override // find one entity and return optional result
	public Optional<Staff> getOptional(String id) throws IllegalArgumentException {
		return rep.findById(id);
	}

	@Override // create and update data of the entity
	public <S extends Staff> S save(S entity) throws IllegalArgumentException {
		Optional<Staff> optional = rep.findById(entity.getUsername());
		if(optional.isEmpty()) {
			return rep.save(entity);
		} else throw new IllegalArgumentException(entity.getUsername()+" already exists, cannot save.");
	}

	@Override // create and update data of the entity
	public <S extends Staff> S update(S entity) throws IllegalArgumentException {
		Optional<Staff> optional = rep.findById(entity.getUsername());
		if(optional.isPresent()) {
			return rep.saveAndFlush(entity);
		} else throw new IllegalArgumentException(entity.getUsername()+" does not exists, cannot update.");
	}

	@Override // remove entity by entity's id
	public void remove(String id) throws IllegalArgumentException {
		rep.deleteById(id);
	}
	
	// @formatter:on

}

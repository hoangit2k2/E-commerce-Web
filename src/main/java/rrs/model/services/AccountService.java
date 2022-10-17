package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rrs.model.entities.Account;
import rrs.model.repositories.AccountRepository;
import rrs.model.utils.InterDAO;

@Service
public class AccountService implements InterDAO<Account, String> {
	
	// @formatter:off
	
	@Autowired private AccountRepository rep;

	@Override // get entities and no conditional
	public List<Account> getList() {
		return rep.findAll();
	}

	@Override // get entities and sort conditional
	public List<Account> getList(Sort sort) {
		return rep.findAll(sort);
	}

	@Override // get page of the entities
	public Page<Account> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override // find one entity and return optional result
	public Optional<Account> getOptional(String id) throws IllegalArgumentException {
		return rep.findById(id);
	}

	@Override // create and update data of the entity
	public <S extends Account> S save(S entity) throws IllegalArgumentException {
		Optional<Account> optional = rep.findById(entity.getUsername());
		if(optional.isEmpty()) {
			return rep.save(entity);
		} else throw new IllegalArgumentException(entity.getUsername()+" already exists, cannot save.");
	}

	@Override // create and update data of the entity
	public <S extends Account> S update(S entity) throws IllegalArgumentException {
		Optional<Account> optional = rep.findById(entity.getUsername());
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

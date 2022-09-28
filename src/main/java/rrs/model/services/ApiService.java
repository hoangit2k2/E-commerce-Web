package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rrs.model.entities.Api;
import rrs.model.repositories.ApiRepository;
import rrs.model.utils.InterDAO;

@Service
public class ApiService implements InterDAO<Api, String> {
	@Autowired private ApiRepository rep;
	
	@Override // get entities and no conditional
	public List<Api> getList() {
		return rep.findAll();
	}

	@Override // get entities and sort conditional
	public List<Api> getList(Sort sort) {
		return rep.findAll(sort);
	}
	
	@Override // get page of the entities
	public Page<Api> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override // find one entity and return optional result
	public Optional<Api> getOptional(String id) throws IllegalArgumentException{
		return rep.findById(id);
	}
	
	@Override // create and update data of the entity
	public <S extends Api> S save(S entity) throws IllegalArgumentException{
		Optional<Api> optional = rep.findById(entity.getId());
		return optional.isEmpty() ? rep.save(entity) : null;
	}
	
	@Override // create and update data of the entity
	public <S extends Api> S update(S entity) throws IllegalArgumentException{
		Optional<Api> optional = rep.findById(entity.getId());
		return optional.isPresent() ? rep.save(entity) : null;
	}

	@Override // remove entity by entity's id
	public void remove(String id) throws IllegalArgumentException{
		rep.deleteById(id);
	}
}

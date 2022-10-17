package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rrs.model.entities.Category;
import rrs.model.repositories.CategoryRepository;
import rrs.model.utils.InterDAO;
import rrs.model.utils.SaveWithRandomId;
import rrs.utils.Random;

@Service
public class CategoryService implements InterDAO<Category, String>, SaveWithRandomId<Category> {
	@Autowired private CategoryRepository rep;
	
	@Override // get entities and no conditional
	public List<Category> getList() {
		return rep.findAll();
	}

	@Override // get entities and sort conditional
	public List<Category> getList(Sort sort) {
		return rep.findAll(sort);
	}
	
	@Override // get page of the entities
	public Page<Category> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override // find one entity and return optional result
	public Optional<Category> getOptional(String id) throws IllegalArgumentException{
		return rep.findById(id);
	}
	
	@Override // create and update data of the entity
	public <S extends Category> S save(S entity) throws IllegalArgumentException {
		return rep.save(entity);
	}
	
	// create and update data of the entity
	@Override
	public <S extends Category> S save(S entity, Boolean randomId) throws IllegalArgumentException {
		if(randomId) { // random until id isEmpty
			Optional<Category> optional; do {
				entity.setId(Random.NumUppLow("", 8));
				optional = rep.findById(entity.getId());
			} while(optional.isPresent());
		} // not random and check category_id
		else if(rep.findById(entity.getId()).isPresent())
			throw new IllegalArgumentException(
				String.format("cannot saved, because %s already exsits", entity.getId())
			);
		return rep.save(entity);
	}
	
	@Override // create and update data of the entity
	public <S extends Category> S update(S entity) throws IllegalArgumentException{
		Optional<Category> optional = rep.findById(entity.getId());
		return optional.isPresent() ? rep.save(entity) : null;
	}

	@Override // remove entity by entity's id
	public void remove(String id) throws IllegalArgumentException{
		rep.deleteById(id);
	}
}

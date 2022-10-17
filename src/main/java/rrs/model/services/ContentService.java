package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rrs.model.entities.Content;
import rrs.model.repositories.ContentRepository;
import rrs.model.utils.InterDAO;
import rrs.model.utils.UpviewContent;

@Service
public class ContentService implements InterDAO<Content, Long>, UpviewContent {
	@Autowired private ContentRepository rep;
	
	@Override // get entities and no conditional
	public List<Content> getList() {
		return rep.findAll();
	}

	@Override // get entities and sort conditional
	public List<Content> getList(Sort sort) {
		return rep.findAll(sort);
	}
	
	@Override // get page of the entities
	public Page<Content> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override // find one entity and return optional result
	public Optional<Content> getOptional(Long id) throws IllegalArgumentException{
		return rep.findById(id);
	}
	
	@Override // find one entity, update view +1 and return optional result
	public Optional<Content> upviews(Long id) throws IllegalArgumentException{
		Optional<Content> optional = rep.findById(id);
		if(optional.isPresent()) rep.upviews(id);
		else throw new IllegalArgumentException("Cannot upview, content's id: "+id+" doesn't exist.");
		return rep.findById(id);
	}
	
	@Override // create and update data of the entity
	public <S extends Content> S save(S entity) throws IllegalArgumentException{
		entity.setId(rep.save(new Content(-1L)).getId()); // get new Id
		return this.update(entity);
	}
	
	@Override // create and update data of the entity
	public <S extends Content> S update(S entity) throws IllegalArgumentException{
		Optional<Content> optional = rep.findById(entity.getId());
		return optional.isPresent() ? rep.save(entity) : null;
	}

	@Override // remove entity by entity's id
	public void remove(Long id) throws IllegalArgumentException {
		rep.deleteById(id);
	}
}

package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rrs.model.entities.Content;
import rrs.model.repositories.ContentRepository;
import rrs.utils.CustomException;

@Service
public class ContentService extends AbstractService<Content, Long> {
	
	@Override
	protected Long getId(Content entity) {
		return entity.getId();
	}
	
	// find one entity, update view +1 and return optional result
	public Optional<Content> upviews(Long id) throws IllegalArgumentException{
		Optional<Content> optional = rep.findById(id);
		if(optional.isPresent()) {
			Content c = optional.get();
			c.setViews(c.getViews()+1);
			super.rep.save(c);
			return optional;
		} else throw new IllegalArgumentException("Cannot upview, content's id: "+id+" doesn't exist.");
	}

	// get list Content by active
	public List<Content> getByActive(Boolean active) throws IllegalArgumentException {
		if(active == null) return super.getList();
		return ((ContentRepository) super.rep).findAllActive(active);
	}
	
	// get list Content by category_id
	public List<Content> getByCategoryId(String category_id) throws IllegalArgumentException {
		if(category_id == null) return super.getList();
		return ((ContentRepository) super.rep).findByCategory(category_id);
	}
	
	// get list Content by account_id
	public List<Content> getByAccountId(String account_id) throws IllegalArgumentException {
		if(account_id == null) return super.getList();
		return ((ContentRepository) super.rep).findByAccountId(account_id);
	}

	@Override // delete the data in LIKES table before delete content
	public void remove(Long id) throws IllegalArgumentException, CustomException {
		int quality = ((ContentRepository) super.rep).deleteLike(id);
		System.out.println("Delete "+quality+" data with content_id: "+id);
		super.remove(id);
	}
	
}

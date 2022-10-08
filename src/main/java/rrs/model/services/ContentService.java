package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rrs.model.entities.Content;
import rrs.model.repositories.ContentRepository;
import rrs.model.utils.SupportContent;

@Service
public class ContentService extends AbstractService<Content, Long> implements SupportContent {

	@Override
	protected Long getId(Content entity) {
		return entity.getId();
	}
	
	@Override // find one entity, update view +1 and return optional result
	public Optional<Content> upviews(Long id) throws IllegalArgumentException{
		Optional<Content> optional = rep.findById(id);
		if(optional.isPresent()) {
			Content c = optional.get();
			c.setViews(c.getViews()+1);
			super.rep.save(c);
			return optional;
		} else throw new IllegalArgumentException("Cannot upview, content's id: "+id+" doesn't exist.");
	}

	@Override // get all Content by active
	public List<Content> getByActive(Boolean active) throws IllegalArgumentException {
		if(active == null) return super.getList();
		return ((ContentRepository) super.rep).findAllActive(active);
	}
	
	@Override
	public List<Content> getByCategoryId(String category_id) throws IllegalArgumentException {
		if(category_id == null) return super.getList();
		return ((ContentRepository) super.rep).findByCategory(category_id);
	}
	
	@Override
	public List<Content> getByAccountId(String account_id) throws IllegalArgumentException {
		if(account_id == null) return super.getList();
		return ((ContentRepository) super.rep).findByAccountId(account_id);
	}
	
}

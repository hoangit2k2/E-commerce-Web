package rrs.model.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import rrs.model.entities.Content;
import rrs.model.utils.UpviewContent;

@Service
public class ContentService extends AbstractService<Content, Long> implements UpviewContent{

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

}
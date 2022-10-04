package rrs.model.services;

import org.springframework.stereotype.Service;

import rrs.model.entities.Api;

@Service
public class ApiService extends AbstractService<Api, String> {
	
	@Override
	protected String getId(Api entity) {
		return entity.getId();
	}

}

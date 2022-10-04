package rrs.model.services;

import org.springframework.stereotype.Service;

import rrs.model.entities.Staff;

@Service
public class StaffService extends AbstractService<Staff, String> {
	
	@Override
	protected String getId(Staff entity) {
		return entity.getUsername();
	}

}

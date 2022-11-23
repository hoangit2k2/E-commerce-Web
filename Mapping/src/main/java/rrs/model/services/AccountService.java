package rrs.model.services;

import org.springframework.stereotype.Service;
import rrs.model.entities.Account;
import rrs.model.repositories.AccountRepository;

@Service
public class AccountService extends AbstractService<Account, String> {
	
	@Override
	protected String getId(Account entity) {
		return entity.getUsername();
	}

	public Account getByEmail(String email) {
		return ((AccountRepository) super.rep).getByEmail(email);
	}

}

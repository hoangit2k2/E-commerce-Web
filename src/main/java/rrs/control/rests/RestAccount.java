package rrs.control.rests;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Account;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/accounts")
public class RestAccount extends AbstractRESTful<Account, String> {
}

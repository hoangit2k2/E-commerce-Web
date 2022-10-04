package rrs.control.rests;

import org.springframework.web.bind.annotation.*;
import rrs.model.entities.Staff;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/staffs")
public class RestStaff extends AbstractRESTful<Staff, String>{
	
}

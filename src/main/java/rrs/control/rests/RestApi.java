package rrs.control.rests;

import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Api;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/api")
public class RestApi extends AbstractRESTful<Api, String>{
}

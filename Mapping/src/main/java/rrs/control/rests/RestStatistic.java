package rrs.control.rests;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import rrs.model.utils.SQLSupport;
import rrs.model.utils.SQLSupport.*;
import rrs.utils.CustomException;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/statistic")
public class RestStatistic {
	
	// @formatter:off
	private @Autowired SQLSupport sql;
	
	/**
	 * @param t is {@link TABLE} to get data
	 * @param c are column names
	 * @param o are order by the column names
	 * @param qty is quantity to select
	 * @param asc sort ascending or descending, default asc = false;
	 * @return List {@link List} Generic is ({@link Map} Generic are String, Object);
	 * @throws JsonProcessingException
	 * @throws CustomException
	 */
	@GetMapping({"/table"}) public ResponseEntity<Object> getData (
			@RequestParam(required = false) TABLE t,
			@RequestParam(required = false) String[] c,
			@RequestParam(required = false) String[] o,
			@RequestParam(required = false) Integer qty,
			@RequestParam(required = false) Boolean desc
	) throws JsonProcessingException, CustomException {
		return ResponseEntity.ok(sql.execute(t, qty, c, o, desc));
	}
	
	@GetMapping({"/ac"}) public ResponseEntity<Object> proc_AC (
			@RequestParam(required = false) PROC t,
			@RequestParam(required = false) Integer qty,
			@RequestParam(required = false) String st,
			@RequestParam(required = false) String ed,
			@RequestParam(required = false) Boolean desc
	) throws JsonProcessingException, CustomException {
		if(st != null) st.replaceAll("%20", " ");
		if(st != null) st.replaceAll("%20", " ");
		return ResponseEntity.ok(sql.execute(t, qty, st, ed, desc));
	}

	// @formatter:on

}

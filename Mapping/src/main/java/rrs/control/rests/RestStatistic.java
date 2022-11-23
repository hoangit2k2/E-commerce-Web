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

	// THỐNG KÊ SỐ NỘI DUNG THEO TÀI KHOẢN
	@GetMapping({"/cs"}) public ResponseEntity<Object> proc_CS (
			@RequestParam(required = false) S_CONTENT t,
			@RequestParam(required = false) Object...p
	) throws JsonProcessingException, CustomException {
		return ResponseEntity.ok(sql.execute(t, p));
	}

	// THỐNG KÊ LƯỢT THÍCH THEO TÀI KHOẢN
	@GetMapping({"/ls"}) public ResponseEntity<Object> proc_LS (
			@RequestParam(required = false) S_LIKE t, 
			@RequestParam(required = false) Object...p
	) throws JsonProcessingException, CustomException {
		return ResponseEntity.ok(sql.execute(t, p));
	}

	// @formatter:on

}

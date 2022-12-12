package com.lovepink.service;
import org.springframework.stereotype.Service;
import com.lovepink.Dao.StatisticRepository;

@Service
public class SQLService extends AbstractSQL implements StatisticRepository {

	@Override
	public Object execute(PROCEDURES proc, Object... params) throws Exception {
		return super.execute(proc.toString(), params);
	}

}

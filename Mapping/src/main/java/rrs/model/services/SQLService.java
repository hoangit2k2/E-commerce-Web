package rrs.model.services;
import org.springframework.stereotype.Service;

import rrs.model.utils.AbstractSQL;
import rrs.model.utils.SQLSupport;
import rrs.utils.CustomException;

@Service
public class SQLService extends AbstractSQL implements SQLSupport {
	
	// @formatter:off
	
	@Override
	public Object execute(String sql, Object...params) throws CustomException {
		return super.execute(sql, params);
	}
	
	@Override 
	public Object execute(PROC proc, Object...params) throws CustomException {
		return super.execute(proc.toString(), params);
	}
	
	@Override 
	public Object execute(TABLE table, String[]columns, String[] orderBy) throws CustomException {
		if(table == null) throw new CustomException("không tồn tại!");
		table.setColumns(columns);
		table.setOrderBy(orderBy);
		return super.execute(table.toString());
	}

	@Override 
	public Object execute(TABLE table, Integer quantity, String[]columns, String[] orderBy, Boolean asc) throws CustomException {
		if(table == null) throw new CustomException("không tồn tại!");
		table.setColumns(columns);
		table.setOrderBy(orderBy);
		if(asc!=null) table.setAsc(asc);
		if(quantity!=null) table.setQuantity(quantity);
		return super.execute(table.toString());
	}
	
	// @formatter:on
}

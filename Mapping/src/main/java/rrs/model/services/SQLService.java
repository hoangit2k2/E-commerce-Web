package rrs.model.services;
import org.springframework.stereotype.Service;

import rrs.model.utils.AbstractSQL;
import rrs.model.utils.SQLSupport;
import rrs.utils.CustomException;

@Service
public class SQLService extends AbstractSQL implements SQLSupport {
	
	// @formatter:off
	
	// CHẠY VỚI CÂU TRUY VẤN SQL
	public Object execute(String sql, Object...params) throws CustomException {
		return super.execute(sql, params);
	}
	
	// CHẠY VỚI SQL CÓ SẴN TRONG enum S_ACCOUNT
	@Override 
	public Object execute(S_ACCOUNT proc, Object...params) throws CustomException {
		return super.execute(proc.toString(), params);
	}
	
	// CHẠY VỚI SQL CÓ SẴN TRONG enum S_CONTENT
	@Override 
	public Object execute(S_CONTENT proc, Object...params) throws CustomException {
		return super.execute(proc.toString(), params);
	}

	// TẠO CÂU TRUY VẤN VỚI CÁC BẢNG TRONG enum TABLE (1) -> Không lấy số lượng + không sắp xếp
	@Override 
	public Object execute(TABLE table, String[]columns, String[] orderBy) throws CustomException {
		if(table == null) throw new CustomException("không tồn tại!");
		table.setColumns(columns);
		table.setOrderBy(orderBy);
		return super.execute(table.toString());
	}

	// TẠO CÂU TRUY VẤN VỚI CÁC BẢNG TRONG enum TABLE (2) -> Thêm điều kiện số lượng và sắp xếp
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

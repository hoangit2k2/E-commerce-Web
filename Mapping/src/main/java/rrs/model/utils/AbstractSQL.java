package rrs.model.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import rrs.utils.CustomException;

public abstract class AbstractSQL {
	
	// @formatter:off
	@Autowired private JdbcTemplate jdbc;
	
	protected Object execute(String sql, Object...params) throws CustomException {
		if(sql == null) throw new CustomException("cannot execute query is null!");
		else if (sql.isEmpty()) throw new CustomException("Cannot execute query is empty!");

		// READ ONLY
		if(params != null && checkComment(sql, params))
			for (int i = 0; i < params.length; i++) 
			if(params[i] instanceof String)
				if(params[i].toString().isEmpty()) params[i] = null; // set null if this parameter is empty
				else params[i] = new StringBuilder("'").append(params[i]).append("'");
		return this.get(String.format(sql, params));
		// TODO insert - update - delete ...
	}
	
	private Object get(String sql) {
		List<Object> list = new LinkedList<>();
		jdbc.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResultSetMetaData mt = rs.getMetaData();
				int count = mt.getColumnCount();
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < count;) map.put(mt.getColumnName(++i), rs.getObject(i));
				list.add(map);
			}
		});
		return list;
	};
	
	private static boolean checkComment(Object...agrs) throws CustomException {
		if(agrs != null) for(Object agr : agrs) if(agr.toString().lastIndexOf("--") > -1)
			throw new CustomException("\n\nRRs-Thông báo 🥵 > Hệ thống phát hiện nghi vấn hack ⚠⚠⚠\n"
				+ "Bạn đừng cố hack hệ thống của chúng tôi làm gì 😑\n\n");
		return true;
	}
	
	// @formatter:on
}

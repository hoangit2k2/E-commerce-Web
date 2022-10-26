package rrs.model.utils;

import rrs.utils.CustomException;

public interface SQLSupport {
	
	public Object execute(PROC proc, Object...params) throws CustomException;
	public Object execute(TABLE table, String[]columns, String[] orderBy) throws CustomException;
	public Object execute(TABLE table, Integer quantity, String[]columns, String[] orderBy, Boolean asc) throws CustomException;
	
	public enum TABLE {
		
		ACCOUNTS, APIs, CATEGORIES, CONTENT_IMAGES, CONTENT_TYPES, CONTENTS, LIKES, STAFFS;
		
		private String[] columns;
		private String[] orderBy;
		int quantity = 1000;
		Boolean desc = null;

		@Override
		public String toString() {
			return createSQL(this);
		}

		public void setColumns(String...columns) throws CustomException {
			if(checkComment((Object[]) columns)) this.columns = columns;
		}

		public void setOrderBy(String...orderBy) throws CustomException {
			if(checkComment((Object[]) orderBy)) this.orderBy = orderBy;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public void setAsc(boolean desc) throws CustomException {
			if(columns == null) throw new CustomException("Không có cột để sắp xếp");
			this.desc = desc;
		}
		
	}

	public enum PROC {
		MIN_MAX("SELECT * FROM VIEW_AC_RANGE"),
		STATISTIC("{CALL PROC_AC(%d, %s, %s, %s)}");

		private String value;
		PROC(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	private static String createSQL(TABLE table) {
		StringBuilder str;
		
		// Select columns or all
		if(table.columns != null) {
			str = new StringBuilder("SELECT TOP ").append(table.quantity).append(' ');
			for(String c : table.columns) str.append(c).append(',');
			str.deleteCharAt(str.length()-1); // delete last ','
			str.append(" FROM ").append(table.name());
		} else str = new StringBuilder("SELECT TOP ").append(table.quantity)
				.append(" * FROM ").append(table.name());

		// order by column
		if(table.orderBy != null) {
			str.append(" ORDER BY ");
			for(String c : table.orderBy) str.append(c).append(',');
			str.deleteCharAt(str.length()-1);
		}
		
		if(table.desc != null) str.append(table.desc ? " DESC" : " ASC");
		return str.toString();
	};
	
	private static boolean checkComment(Object...agrs) throws CustomException {
		if(agrs != null) for(Object agr : agrs) if(agr.toString().lastIndexOf("--") > -1)
			throw new CustomException("\n\nRRs-Thông báo 🥵 > Hệ thống phát hiện nghi vấn hack ⚠⚠⚠\n"
				+ "Bạn đừng cố hack hệ thống của chúng tôi làm gì 😑\n\n");
		return true;
	}

}

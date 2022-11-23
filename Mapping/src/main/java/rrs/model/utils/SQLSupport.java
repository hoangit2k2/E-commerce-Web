package rrs.model.utils;

import rrs.utils.CustomException;

public interface SQLSupport {

	public Object execute(S_CONTENT proc, Object...params) throws CustomException;
	public Object execute(S_LIKE proc, Object...params) throws CustomException;
	public Object execute(TABLE table, String[]columns, String[] orderBy) throws CustomException;
	public Object execute(TABLE table, Integer quantity, String[]columns, String[] orderBy, Boolean asc) throws CustomException;
	
	
	public enum S_CONTENT {
		MIN_MAX("SELECT * FROM VIEW_CS_RANGE"),
		/**
		 * Truyền vào giá trị (số lương, ngày bắt đầu, ngày kết thúc, sắp xếp)<br/>
		 * EX: (10, '2020-12-15', '2022-12-15')
		 */
		CBA("{CALL PROC_CBA(?, ?, ?, ?)}"),
		
		/**
		 * Chọn theo 1(YEAR) | 2(MONTH) | 3(DAY)<br/>
		 * Truyền vào giá trị (1 | 2 | 3, ngày bắt đầu, ngày kết thúc)<br/>
		 * EX: (2, '2020-12-15', '2022-12-15')
		 */
		CBT("{CALL PROC_CBT(?, ?, ?)}");// ? = 1 || 2 || 3

		private String value;
		S_CONTENT(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	public enum S_LIKE {
		MIN_MAX("SELECT * FROM VIEW_LS_RANGE"),
		/**
		 * Truyền vào giá trị (số lượng, ngày bắt đầu, ngày kết thúc)<br/>
		 * EX(15, '2020-12-15', '2022-12-15')
		 */
		LBA("{CALL PROC_LBA(?, ?, ?)}"),
		LBC("{CALL PROC_LBC(?, ?, ?)}"),
		LBT("{CALL PROC_LBT(?, ?, ?)}");
		
		private String value;
		S_LIKE(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	public enum TABLE {
		
		ACCOUNTS, CATEGORIES, CONTENT_IMAGES, CONTENT_TYPES, CONTENTS, LIKES, STAFFS;
		
		private String[] columns;
		private String[] orderBy;
		int quantity = 1000;
		Boolean desc = null;

		@Override
		public String toString() {
			return createSQL(this);
		}

		public void setColumns(String...columns) throws CustomException {
			this.columns = columns;
		}

		public void setOrderBy(String...orderBy) throws CustomException {
			this.orderBy = orderBy;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public void setAsc(boolean desc) throws CustomException {
			if(columns == null) throw new CustomException("Không có cột để sắp xếp");
			this.desc = desc;
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
	

}

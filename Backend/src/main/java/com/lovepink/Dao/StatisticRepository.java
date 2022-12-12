package com.lovepink.Dao;

public interface StatisticRepository {

	public Object execute(PROCEDURES proc, Object...params) throws Exception;
	
	public enum PROCEDURES {
		RANGE("SELECT * FROM VIEW_SO_RANGE"),
		UPCONTENT("{CALL PROC_SS_TIME(?, ?, ?, ?)}"),
		LIKE("EXEC S_LIKE ?"),
		TURNOVER("EXEC S_TURNOVER");

		private String value;
		PROCEDURES(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
}

package com.mob.orm;

public class TestDbModel{
	public enum TestField implements DbField{
		test_id("test_id"),
		name("name"),
		location("location");
		private String column;
		TestField(String column){ this.column = column; }
		public String table(){ return "TestDb"; }
		public String column(){ return table() + "." + this.column; }
	}
}

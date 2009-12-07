package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Set;
import java.util.List;

public class Constants{

	public static final Function<Clause, String> sqlGetter = 
		new Function<Clause, String>(){
			public String apply(Clause c){ 
				return c.getSql();
			}
		};
	
	public static final Function<Clause, Iterable<Object>> valuesGetter = 
		new Function<Clause, Iterable<Object>>(){
			public Iterable<Object> apply(Clause c){ 
				return c.getValues();
			}
		};

	public static final Function<DbField, String> colNameGetter = 
		new Function<DbField, String>(){
			public String apply(DbField f){ 
				return f.column();
			}
		};

	public static final Function<DbField, String> tblNameGetter = 
		new Function<DbField, String>(){
			public String apply(DbField f){ 
				return f.table();
			}
		};

	public static final Joiner joinCommas = Joiner.on(",");

	public static String getQuestionMarks(int num){
		StringBuilder qMarks = new StringBuilder("(?");
		for(int i = 0; i < num - 1; i++ ){
			qMarks.append(",?");
		}
		qMarks.append(")");
		return qMarks.toString();
	}

	public static final Function<Object, String> toString = 
		new Function<Object, String>(){
			public String apply(Object o){ 
				if( o == null ) return "[null]";
				return o.toString();
			}
		};

}

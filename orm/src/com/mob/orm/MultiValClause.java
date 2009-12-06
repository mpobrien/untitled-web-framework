package com.mob.orm;
import java.util.List;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Arrays;

public class MultiValClause extends Clause{

	private final List<Object> values;
	private final DbField field;

	public MultiValClause(DbField field, Object... values){
		this.field = field;
		this.values = Arrays.asList( values );
	}

	public MultiValClause(DbField field, List<Object> values){
		this.field = field;
		this.values = values;
	}

	public String getSql(){
		if( values == null || values.isEmpty() ) return "";
		return this.field.column() + 
		       (this.not ? " NOT IN " : " IN ") + Constants.getQuestionMarks( values.size() );
	}
	
	@Override
	public Iterable<Object> getValues(){
		return values;
	}

}

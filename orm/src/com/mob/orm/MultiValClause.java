package com.mob.orm;
import java.util.List;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Arrays;

public class MultiValClause extends Clause{

	private final Iterable<Object> values;
	private final DbField field;

	public MultiValClause(DbField field, Object... values){
		this.field = field;
		this.values = Arrays.asList( values );
	}

	public MultiValClause(DbField field, Iterable<Object> values){
		this.field = field;
		this.values = values;
	}

	public String getSql(){
		if( values == null || Iterables.isEmpty(values) ) return "";
		return this.field.column() + 
		       (this.not ? " NOT IN " : " IN ") + Constants.getQuestionMarks( Iterables.size(values) );
	}
	
	@Override
	public Iterable<Object> getValues(){
		return values;
	}

}

package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;

public class Clauses{

    public static Clause eq(DbField field, Object value){
        return new EqualityClause(EqualityClause.Operator.EQUALS, field, value);
    } 

	public static ClauseGenerator eq(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return eq( field, o ); } };
	}

    public static Clause notEq(DbField field, Object value){ 
        return new EqualityClause(EqualityClause.Operator.NOT_EQUALS, field, value);
	} 

	public static ClauseGenerator notEq(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return notEq( field, o ); } };
	}

    public static Clause gt(DbField field, Object value){ 
        return new EqualityClause(EqualityClause.Operator.GRTR_THAN, field, value);
	}

	public static ClauseGenerator gt(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return gt( field, o ); } };
	}

    public static Clause gte(DbField field, Object value){ 
        return new EqualityClause(EqualityClause.Operator.GRTR_THAN_EQUAL, field, value);
	}

	public static ClauseGenerator gte(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return gte( field, o ); } };
	}
	
    public static Clause lt(DbField field, Object value){ 
        return new EqualityClause(EqualityClause.Operator.LESS_THAN, field, value);
	}

	public static ClauseGenerator lt(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return lt( field, o ); } };
	}

    public static Clause lte(DbField field, Object value){ 
        return new EqualityClause(EqualityClause.Operator.LESS_THAN_EQUAL, field, value);
	}

	public static ClauseGenerator lte(final DbField field){
		return new ClauseGenerator(){ public Clause make(Object o){ return lte( field, o ); } };
	}

	public static Clause any(Clause... clauses){
		return new CompoundClause(CompoundClause.CompoundType.ANY, clauses);
	}

	public static Clause all(Clause... clauses){
		return new CompoundClause(CompoundClause.CompoundType.ALL, clauses);
	}

	public static Clause not(Clause clause){
		return clause.negate();
	}

	public static Clause in(DbField field, Object... values){
		return new MultiValClause(field, values);
	}

	public static Clause in(DbField field, Iterable<Object> values){
		return new MultiValClause(field, values);
	}

	public static Clause notIn(DbField field, Object... values){
		return new MultiValClause(field, values).negate();
	}

	public static Clause isNull( DbField field ){
		return new NullCheckClause( NullCheckClause.NullCheckType.NULL, field );
	}

	public static Clause isNotNull( DbField field ){
		return new NullCheckClause( NullCheckClause.NullCheckType.NOTNULL, field );
	}

}

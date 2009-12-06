package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.List;
import java.util.Arrays;

public class CompoundClause extends Clause{

	private final List<Clause> clauses;
	private final CompoundType opType;

    public enum CompoundType{
        ANY (" OR "),
        ALL (" AND ");
        private final String op;
        CompoundType(String op){ this.op = op; }
		public String op(){ return this.op; }
		public CompoundType negate(){
			if( this == ANY ) return ALL;
			return ANY;
		}
    }

	public CompoundClause(CompoundType opType, Clause... clauses){
		this.clauses = Arrays.asList(clauses);
		this.opType = opType;
	}

	public CompoundClause(CompoundType opType, List<Clause> clauses){
		this.clauses = clauses;
		this.opType = opType;
	}

	@Override
	public String getSql(){
		CompoundType opSql = (this.not ? this.opType.negate() : this.opType);
		return "(" + Joiner.on( opSql.op() ).join( Lists.transform( this.clauses, Constants.sqlGetter ) ) + ")";
	}


	@Override
	public Iterable<Object> getValues(){
		return Iterables.concat( Lists.transform( clauses, Constants.valuesGetter ) );
	}


}

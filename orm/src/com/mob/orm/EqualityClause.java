package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;

public class EqualityClause extends Clause{
	private final Operator opType;
    private final DbField field;
    private final Object value;
   
    public enum Operator{//{{{
        EQUALS("="),
        NOT_EQUALS("!="),
        LESS_THAN("<"),
        LESS_THAN_EQUAL("<="),
        GRTR_THAN(">"),
        GRTR_THAN_EQUAL(">=");
        private final String op;
        Operator(String op){ this.op = op; }
		public String op(){ return this.op; }
    }//}}}

   public EqualityClause(Operator opType, DbField field, Object value){
       this.opType = opType;
       this.field = field;
       this.value = value;
   }

   @Override
   public String getSql(){
	   //TODO we need to somehow convert the value to an appropriate string for sql
	   //return this.field.column() + this.opType.op() + value.toString();
	   return (this.not ? "NOT " : "") + this.field.column() + this.opType.op() + "?";
   }

   @Override
   public Iterable<Object> getValues(){
	   return ImmutableList.of( this.value );
   }


}

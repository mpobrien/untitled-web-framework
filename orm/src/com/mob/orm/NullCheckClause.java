package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;

public class NullCheckClause extends Clause{
	private final NullCheckType opType;
    private final DbField field;
   
    public enum NullCheckType{//{{{
		NULL(" IS NULL"),
		NOTNULL(" IS NOT NULL");
        private final String op;
        NullCheckType(String op){ this.op = op; }
		public String op(){ return this.op; }
		public NullCheckType negate(){
			if( this == NULL ) return NOTNULL;
			return NULL;
		}
    }//}}}

   public NullCheckClause(NullCheckType opType, DbField field){
       this.opType = opType;
       this.field = field;
   }

   @Override
   public String getSql(){
		NullCheckType opSql = (this.not ? this.opType.negate() : this.opType);
 		return this.field.column() + this.opType.op();
   }

   @Override
   public Iterable<Object> getValues(){
		return Lists.newArrayListWithCapacity( 0 );
   }


}

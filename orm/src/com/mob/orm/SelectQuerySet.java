package com.mob.orm;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import com.google.common.base.*;
import com.google.common.collect.*;

public class SelectQuerySet extends QuerySet{
	private final List<DbField> fields;
	private final Set<String> tableNames;
	private Clause whereClause;
	private Integer offset = null;
	private Integer count = null;
	private List<DbField> orderByFields = null;
	private Ordering orderType = Ordering.UNDEF;

	private enum Ordering{//{{{
		UNDEF(""),
		ASC(" ASC"),
		DESC(" DESC");
		private String token;
		Ordering(String token){ this.token = token; }
		public String token(){ return this.token; }
	}//}}}

	public SelectQuerySet(List<DbField> fields){//{{{
		this.fields = fields;
		this.tableNames = extractTableNames( this.fields );
	}//}}}

	public SelectQuerySet(DbField... fields){//{{{
		this.fields = Arrays.asList( fields );
		this.tableNames = extractTableNames( this.fields );
	}//}}}

	public SelectQuerySet where(Clause whereClause){//{{{
		this.whereClause = whereClause;
		return this;
	}//}}}

	public SelectQuerySet where(Clause... whereClauses){//{{{
		this.whereClause = Clauses.all( whereClauses );
		return this;
	}//}}}

	public SelectQuerySet orderBy(List<DbField> orderByFields){//{{{
		this.orderByFields = orderByFields;
		return this;
	}//}}}

	public SelectQuerySet orderBy(DbField... orderByFields){//{{{
		this.orderByFields = Arrays.asList( orderByFields );
		return this;
	}//}}}

	public SelectQuerySet orderBy(Ordering orderType, List<DbField> orderByFields){//{{{
		this.orderByFields = orderByFields;
		this.orderType = orderType;
		return this;
	}//}}}

	public SelectQuerySet orderBy(Ordering orderType, DbField... orderByFields){//{{{
		this.orderByFields = Arrays.asList( orderByFields );
		this.orderType = orderType;
		return this;
	}//}}}

	public SelectQuerySet orderByDesc(DbField... orderByFields){//{{{
		this.orderByFields = Arrays.asList( orderByFields );
		this.orderType = Ordering.DESC;
		return this;
	}//}}}

	public SelectQuerySet orderByDesc(List<DbField> orderByFields){//{{{
		this.orderByFields = orderByFields;
		this.orderType = Ordering.DESC;
		return this;
	}//}}}

	public SelectQuerySet limit( int limit ){//{{{
		this.offset = null;
		this.count = limit;
		return this;
	}//}}}

	public SelectQuerySet page(int offset, int count){//{{{
		this.offset = offset;
		this.count = count;
		return this;
	}//}}}

	public String getSql(){//{{{
		String sqlOutput = "SELECT " + 
		                   Constants.joinCommas.join( extractColumnNames(this.fields) ) +
						   " FROM " + 
		                   Constants.joinCommas.join( extractTableNames(this.fields) );
		if( this.whereClause != null ){
			sqlOutput += " WHERE " + this.whereClause.getSql();
		}
		if( this.orderByFields != null && !this.orderByFields.isEmpty() ){
			sqlOutput += " ORDER BY " + Constants.joinCommas.join( extractColumnNames(this.orderByFields) ) 
			             + this.orderType.token();
		}
		if( this.count != null ){
			sqlOutput += " LIMIT " + this.count;
			if( this.offset != null ){
				sqlOutput += " OFFSET " + this.offset;
			}
		}
		return sqlOutput;
	}//}}}

	public Iterable<Object> getValues(){//{{{
		if( this.whereClause == null ){
			return null;
		}else{
			return this.whereClause.getValues();
		}
	}//}}}

}

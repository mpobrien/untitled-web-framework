package com.mob.orm;
import java.util.List;
import com.google.common.base.*;
import com.google.common.collect.*;

public class TableInfo{

    private final String tableName;
	private final List<ColumnInfo> columns;
	private final List<ColumnInfo> primaryKeys;

    public TableInfo(String tableName, List<ColumnInfo> columns){
        this.tableName = tableName;
		this.columns = columns;
		this.primaryKeys = Lists.newArrayList( Iterables.filter( this.columns, new Predicate<ColumnInfo>(){
																					public boolean apply(ColumnInfo c){
																						return c.getIsPrimaryKey(); 
																					} } ) );
    }

	public List<ColumnInfo> getPrimaryKeys(){
		return this.primaryKeys;
	}

	public List<ColumnInfo> getNonAutoincCols(){
		return Lists.newArrayList( Iterables.filter( this.columns, new Predicate<ColumnInfo>(){
																		public boolean apply(ColumnInfo c){
																			return !c.getIsAutoIncrement();
																		}
																	}) );
	}

	public String toString(){
		String retVal = "Table: " + tableName;
		if( columns != null && !columns.isEmpty() ){
			retVal += "\n\t" + Joiner.on("\n\t").join(columns);
		}
		return retVal + "\n";
	}

	public List<ColumnInfo> getCols(){
		return this.columns;
	}

	public String getClassname(){
		return camelize( this.tableName, true );
	}

	public static String camelize(String db, boolean includeFirst){
		String parts[] = db.split("_");
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		for( String p : parts ){
			if(!flag && !includeFirst){
				sb.append( p );
				flag = true;
				continue;
			}
			sb.append( capitalizeFirst( p ) );
		}
		return sb.toString();
	}

	public static String capitalizeFirst(String s){
		if( s==null || s.equals("") ) return s;
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}

	public String getTablename(){
		return this.tableName;
	}

}

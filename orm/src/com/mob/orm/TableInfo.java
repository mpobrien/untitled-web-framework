package com.mob.orm;
import java.util.List;
import com.google.common.base.*;
import com.google.common.collect.*;

public class TableInfo{

    private final String tableName;
	private final List<ColumnInfo> columns;

    public TableInfo(String tableName, List<ColumnInfo> columns){
        this.tableName = tableName;
		this.columns = columns;
    }

	public String toString(){
		String retVal = "Table: " + tableName;
		if( columns != null && !columns.isEmpty() ){
			retVal += "\n\t" + Joiner.on("\n\t").join(columns);
		}
		return retVal;
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

	public ColumnInfo getPrimaryKey(){
		for( ColumnInfo c : this.columns ){
			if( c.getIsPrimaryKey() ) return c;
		}
		return null;
	}

	public String getTablename(){
		return this.tableName;
	}

}

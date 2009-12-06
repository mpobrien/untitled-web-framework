package com.mob.orm;
import java.util.regex.*;

public class ColumnInfo{

    private final String columnName;
    private final String sqlType;
    private final String nullable;
    private final String key;
    private final String defaultVal;
    private final String extra;
    private final String collation;

	private final JavaType javaType;

	public enum JavaType{
		BIGDECIMAL("BigDecimal"),
		BIGINTEGER("BigInteger"),
		BOOLEAN("Boolean"),
		BYTEARRAY("byte[]"),
		DATE("Date"),
		DOUBLE("Double"),
		FLOAT("Float"),
		INTEGER("Integer"),
		LONG("Long"),
		STRING("String"),
		TIME("Date"),
		TIMESTAMP("Date");
		private String javaClass;
		JavaType(String javaClass){ this.javaClass = javaClass; }
		public String getJavaClass(){ return this.javaClass; }
	}
	
    public ColumnInfo(String columnName, String sqlType, String collation, String nullable, String key, String defaultVal, String extra, String privileges, String comment ) throws Exception{
        this.columnName = columnName;
    	this.sqlType = sqlType.toLowerCase().trim();
    	this.nullable = nullable.toLowerCase().trim();
		this.collation = collation!= null ? collation.toLowerCase().trim() : null;
    	this.key = key != null ? key.toLowerCase().trim() : null;
    	this.defaultVal = defaultVal;
    	this.extra = extra;
		this.javaType = getJavaType();
    }

    public ColumnInfo( BasicResultSet row ) throws Exception{
		this( row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getString(4), row.getString(5), row.getString(6), row.getString(7),row.getString(8) );
    }

	public boolean getIsNullable(){
		return this.nullable.equals("yes");
	}

	public boolean getIsPrimaryKey(){
		return this.key != null && this.key.equals("pri");
	}

	public boolean getIsUnique(){
		return this.key != null && this.key.equals("uni");
	}

	public String getJavaTypeStr(){
		try{
		    return this.getJavaType().getJavaClass();
		}catch(Exception e){
			//TODO fix
			e.printStackTrace();
			return "";
		}
	}

	public String getJavaName(){
		return TableInfo.camelize( this.columnName, false );
	}

	public String getJavaNameCaps(){
		return TableInfo.camelize( this.columnName, true );
	}

	public String getName(){
		return this.columnName;
	}

	private JavaType getJavaType() throws Exception{//{{{
		Matcher bitMatcher = Pattern.compile("bit(\\(\\d+\\))?").matcher( this.sqlType );
		Matcher tinyMatcher = Pattern.compile("tinyint(\\(\\d+\\))?").matcher( this.sqlType );;
		Matcher intMatcher =  Pattern.compile("(smallint|int|mediumint|integer|bigint)(\\(\\d+\\))?\\s*(unsigned)?").matcher(this.sqlType); 

		if( this.sqlType.matches("(tinytext|text|mediumtext|longtext|enum|set)") ){
			return JavaType.STRING;
		}else if( this.sqlType.matches("(char(\\(.*\\))?|varchar(\\(.*\\))?)") ){
			if( this.collation != null && this.collation.equals( "binary" ) ){
				return JavaType.BYTEARRAY;
			}else{
				return JavaType.STRING;
			}
		}else if( bitMatcher.matches() ){
			if( bitMatcher.group( 1 ) == null || new Integer( bitMatcher.group( 1 ) ).equals( 1 ) ){
				return JavaType.BOOLEAN;
			}else{
				return JavaType.BYTEARRAY;
			}
	    } else if( tinyMatcher.matches() ){
			Integer tinySize = tinyMatcher.group( 1 ) == null ? new Integer( -1 ) : new Integer( tinyMatcher.group( 1 ) );
			if( tinySize < 0 || tinySize.equals( 1 ) ){
				return JavaType.BOOLEAN;
			}else{
				return JavaType.INTEGER;
			}
		} else if( sqlType.matches("(bool|boolean)") ){
			return JavaType.BOOLEAN;
		} else if( intMatcher.matches() ){
			String type = intMatcher.group( 1 );
			System.out.println("unsigned check from " + intMatcher.group(0) + ": " + intMatcher.group(3));
			boolean isUnsigned = intMatcher.group( 3 ) != null; 
			if( type.equals("smallint") ) return JavaType.INTEGER;
			if( type.matches("(mediumint|int|integer)") ) return isUnsigned ? JavaType.LONG: JavaType.INTEGER;
			if( type.matches("bigint") ) return isUnsigned ? JavaType.BIGINTEGER: JavaType.LONG;
		} else if( sqlType.matches("float(\\(\\d+\\))?") ){
			return JavaType.FLOAT;
		} else if( sqlType.matches("double(\\(\\d+\\))?") ){
			return JavaType.DOUBLE;
		} else if( sqlType.matches("decimal(\\(\\d+\\))?") ){
			return JavaType.BIGDECIMAL;
		} else if( sqlType.matches("(date|datetime)") ){
			return JavaType.DATE;
		} else if( sqlType.matches("timestamp\\(\\d+\\)") ){
			return JavaType.TIMESTAMP;
		} else if( sqlType.matches("timestamp") ){
			return JavaType.TIME;
		} else if( sqlType.matches("(tinyblob|blob|mediumblob|longblob)") ){
			return JavaType.BYTEARRAY;
		} else if( sqlType.matches("(binary|varbinary)(\\(\\d+\\))?") ){
			return JavaType.BYTEARRAY;
		}
		throw new Exception("couldn't figure out a java type for this column!");
	}//}}}

	public String toString(){
		return this.columnName + " " + this.sqlType + " " + this.javaType + " Nullable: " + this.getIsNullable() + " (" + this.getJavaTypeStr() + ": " + getJavaName() + ")";
	}

}

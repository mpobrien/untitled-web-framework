package com.mob.orm;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import javax.sql.*;

public class BasicResultSet {

	// XXX - a hack to convert jdbc strings from cp1252 to iso-8859-1
    //private static final boolean useEncodingHack = "true".equals( Global.getInstance().getProperty( "jdbc.encoding_hack" ) );
	
	// java types
	private enum JavaDatatype { UNDEFINED, NUMBER, DATE, BYTE, BYTES, BIGD };
	// meta data
	private ResultSetMetaData md = null;

	private int rows  = 0;
	private int cols  = 0;
	private int index = -1;

	private Object[][] data;
	private JavaDatatype[] types;
	private String[] colNames;

	public BasicResultSet( ResultSet rs ) throws SQLException {
		if ( rs == null )
			throw new IllegalArgumentException( "received null result set");

		rs.last();
		rows = rs.getRow();
		rs.beforeFirst();
		md = rs.getMetaData();
		cols = md.getColumnCount();

		data  = new Object[rows][cols];
		types = new JavaDatatype[cols];
		colNames = new String[cols];
		Arrays.fill( types, JavaDatatype.UNDEFINED );

		for ( int i = 0; i < cols; i++ ) {
			colNames[i] = md.getColumnName(i+1);
		}

		// fill data
		int row = 0;
		while ( rs.next() ) {
			for ( int i = 0; i < cols; i++ ) {
				data[row][i] = rs.getObject( i + 1 );
				
// 				if ( useEncodingHack && data[row][i] instanceof String ) {
// 					try {
// 						data[row][i] = new String( rs.getString( i + 1 ).getBytes( "Cp1252" ), "ISO-8859-1" );
// 					}
// 					catch ( UnsupportedEncodingException e ) { }
// 				}
			}
			row++;
		}
	}

	public ResultSetMetaData getMetaData() {
		return this.md;
	}

	public BasicResultSet( Object[][] data ) {

		if ( data == null || data[0] == null )
			throw new IllegalArgumentException( "received null data" );

		this.data = data;

		rows = this.data.length;
		if ( rows > 0 )
			cols = this.data[0].length;

		types = new JavaDatatype[cols];
		Arrays.fill( types, JavaDatatype.UNDEFINED );
	}

	public int getRows() { return this.rows; }
	public int getCols() { return this.cols; }
	public String[] getColNames() { return this.colNames; }
	public int getCurRow() { return this.index; }
	public int size() { return this.rows; }

	// move around
	public boolean next() {
		index++;
		return ( index < rows );
	}

	public void setCurRow( int index ) { this.index = index; }

	public boolean first() {
		index = 0;
		return true;
	}

	public boolean last() {
		index = rows - 1;
		return true;
	}

	public boolean isBeforeFirst() {
		return ( index == -1 );
	}

	public boolean isFirst() {
		return ( index == 0 );
	}

	public boolean isLast() {
		return ( index + 1 == rows );
	}


	// getters below here
	
	public String getString( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		if ( types[col] == JavaDatatype.BYTES ) {
			return new String( (byte[])obj );
		}
		else if ( obj instanceof byte[] ) {
			types[col] = JavaDatatype.BYTES;
			return new String( (byte[])obj );
		}
		else {
			// rely on toString for all other objects
			return data[index][col].toString();
		}
	}

	public Byte getByte( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Byte( ((Number)obj).byteValue() );
		}
		else if ( types[col] == JavaDatatype.BYTE ) {
			return (Byte)obj;
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Byte( ((Number)obj).byteValue() );
		}
		else if ( obj instanceof Byte ) {
			types[col] = JavaDatatype.BYTE;
			return (Byte)obj;
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a Byte" );
		}
	}

	public java.math.BigDecimal getBigDecimal( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.BIGD ) {
			return (java.math.BigDecimal)obj;
		}
		else if ( obj instanceof java.math.BigDecimal ) {
			types[col] = JavaDatatype.BIGD;
			return (java.math.BigDecimal)obj;
		}
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new java.math.BigDecimal( ((Number)obj).longValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new java.math.BigDecimal( ((Number)obj).longValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a BigDecimal" );
		}
	}

	public Integer getInteger( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Integer( ((Number)obj).intValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Integer( ((Number)obj).intValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to an Integer" );
		}
	}

	public Long getLong( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Long( ((Number)obj).longValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Long( ((Number)obj).longValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a Long" );
		}
	}

	public Float getFloat( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Float( ((Number)obj).floatValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Float( ((Number)obj).floatValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a Float" );
		}
	}

	public Double getDouble( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Double( ((Number)obj).doubleValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Double( ((Number)obj).doubleValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a Double" );
		}
	}

	public Short getShort( int col ) {
		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		// only do this for valid numeric types
		// else log descriptive error and return a zero??
		if ( types[col] == JavaDatatype.NUMBER ) {
			return new Short( ((Number)obj).shortValue() );
		}
		else if ( obj instanceof Number ) {
			types[col] = JavaDatatype.NUMBER;
			return new Short( ((Number)obj).shortValue() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-numeric value to a Short" );
		}
	}

	public java.util.Date getDate( int col ) {

		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		if ( types[col] == JavaDatatype.DATE ) {
			return new java.util.Date( ((java.util.Date)obj).getTime() );
		}
		else if ( obj instanceof java.util.Date ) {
			types[col] = JavaDatatype.DATE;
			return new java.util.Date( ((java.util.Date)obj).getTime() );
		}
		else {
			throw new ClassCastException( "Attempting to coerce non-date value to a Date" );
		}
	}

	public byte[] getBytes( int col ) {

		if ( data[index][col] == null )
			return null;

		Object obj = data[index][col];

		if ( types[col] == JavaDatatype.BYTES ) {
			return (byte[])obj;
		}
		else if ( obj instanceof byte[] ) {
			types[col] = JavaDatatype.BYTES;
			return (byte[])obj;
		}
		else {
			throw new ClassCastException( "Attempting to cast non-byte[] value to a byte[]" );
		}
	}

	public Object getObject( int col ) {
		return data[index][col];
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("");
		for( int i=0; i < rows; i++){
			for ( int j = 0; j < cols; j++ ) {
				sb.append(data[i][j] != null ? data[i][j].toString() : "[null]").append(",") ;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

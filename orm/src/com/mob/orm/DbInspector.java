package com.mob.orm;
import java.util.*;
import freemarker.core.*;
import freemarker.template.*;
import java.io.*;

public class DbInspector{

    public static void main(String args[]) throws Exception{
		if( args.length != 2 ){
			System.out.println("Usage: dbinspector <outputdirectory> <packageName>");
		}
        String directory = args[0];
		String packageName = args[1];

        ConnectionProvider cp = new TestConnectionProvider("com.mysql.jdbc.Driver","jdbc:mysql:///mike_testing","root","turtl3");
        QueryExecutor qe = new QueryExecutor( cp );
        BasicResultSet brs = qe.execute( "show tables");
		List<TableInfo> tables = new ArrayList<TableInfo>();
		while( brs.next() ){
			String tableName = brs.getString( 0 );
			BasicResultSet brs2 = qe.execute( "show full columns from " + tableName);
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
			while( brs2.next() ){
				ColumnInfo col = new ColumnInfo( brs2 );
				columns.add( col );
			}
			tables.add( new TableInfo(tableName, columns ) );
		}

		Configuration config = new Configuration();
		config.setObjectWrapper( ObjectWrapper.BEANS_WRAPPER );
		Template template = config.getTemplate("modeltemplate.fmt");  

		for( TableInfo table : tables ){
			File f = new File( directory + "/" + table.getClassname() + ".java" );
			Writer out = new OutputStreamWriter( new FileOutputStream( f ) );
			Map context = new HashMap();
			context.put("package", packageName);
			context.put("table", table );
			template.process(context, out);
		}
    }

}

package com.mob.orm;
import java.util.*;
import freemarker.core.*;
import freemarker.template.*;
import java.io.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Properties;

public class DbInspector{

    public static void main(String args[]) throws Exception{
		if( args.length != 1 && args.length != 3 ){
			System.exit(2);
		}

		Properties propsFile = new Properties();
		FileInputStream fis = null;
		fis = new FileInputStream( new File( args[0] ));
		propsFile.load(fis);    
		fis.close();

		String driver = propsFile.getProperty( "db.driver" );
		String jdbc   = propsFile.getProperty( "db.jdbc" );
		String user   = propsFile.getProperty( "db.user" );
		String pw     = propsFile.getProperty( "db.pw" );
		System.out.println("driver: " + driver);
		System.out.println("jdbc: " + jdbc);
		System.out.println("user: " + user);
		System.out.println("pw: " + pw);

        ConnectionProvider cp = new TestConnectionProvider(driver, jdbc, user, pw);
		List<TableInfo> tables = getTables( cp );
		if( args.length == 3 ){
			System.out.println("Usage: dbinspector <outputdirectory> <packageName>");
			String directory = args[1];
			String packageName = args[2];
			Configuration config = new Configuration();
			config.setObjectWrapper( ObjectWrapper.BEANS_WRAPPER );
			Template template = config.getTemplate("modeltemplate.ftl");  

			for( TableInfo table : tables ){
				File f = new File( directory + "/" + table.getClassname() + ".java" );
				Writer out = new OutputStreamWriter( new FileOutputStream( f ) );
				Map context = new HashMap();
				context.put("package", packageName);
				context.put("table", table );
				template.process(context, out);
			}
		}else{
			for( TableInfo table : tables ){
				System.out.println( table );
			}
		}
    }

	public static List<TableInfo> getTables(ConnectionProvider cp) throws Exception{//{{{
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
		return tables;
	}//}}}

// 	public static void figureOutKeys(List<TableInfo> tables){
// 		Map<String, TableInfo> tableNames = Maps.uniqueIndex( tables, new Function<TableInfo,String>(){
// 				public String apply(TableInfo t){ return t.getTablename(); } });
// 
// 		for(TableInfo t1 : tables){
// 			for(TableInfo t2 : tables){
// 				if(t1 == t2) continue;
// 				List<String>
// 			}
// 		}
// 	}

}

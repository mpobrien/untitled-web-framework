package com.mob.orm;
import com.mob.orm.ConnectionProvider;
import com.mchange.v2.c3p0.*;
import java.sql.*;
import javax.sql.*;


public class TestConnectionProvider implements ConnectionProvider{

    private final ComboPooledDataSource cpds;

    public TestConnectionProvider(  String driver, String jdbc, String user, String password){
		this.cpds = new ComboPooledDataSource();
		try{
			cpds.setDriverClass( driver ); //loads the jdbc driver            
			cpds.setJdbcUrl( jdbc );
			cpds.setUser( user );                                  
			cpds.setPassword( password );                                  
		}catch(Exception e){
			//TODO handle this. Also, update the interface.
			e.printStackTrace();
		}
    }

	@Override
    public Connection getConnection(){
		try{
			return this.cpds.getConnection();
		}catch(Exception e){
			//TODO handle this. Also, update the interface.
			e.printStackTrace();
			return null;
		}
    }
}

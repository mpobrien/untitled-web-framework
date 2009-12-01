package com.mob.web;
import com.mob.orm.ConnectionProvider;
import com.mchange.v2.c3p0.*;
import java.sql.*;
import javax.sql.*;
import com.google.inject.*;
import com.google.inject.name.*;


public @Singleton class SimpleConnectionProvider implements ConnectionProvider{

    private final ComboPooledDataSource cpds;

    @Inject
    public SimpleConnectionProvider( @Named("db.driver") String driver,
								 @Named("db.jdbc") String jdbc,
								 @Named("db.user") String user,
								 @Named("db.pw") String password){
		this.cpds = new ComboPooledDataSource();
		try{
			cpds.setDriverClass( driver ); //loads the jdbc driver            
			cpds.setJdbcUrl( jdbc );
			cpds.setUser( user );                                  
			cpds.setPassword( password );                                  
			// the settings below are optional -- c3p0 can work with defaults
			// cpds.setMinPoolSize(5);                                     
			// cpds.setAcquireIncrement(5);
			// cpds.setMaxPoolSize(20);
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

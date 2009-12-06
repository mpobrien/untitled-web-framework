package com.mob.orm;
import java.sql.*;
import javax.sql.*;

public class ModelManagerFactory<T extends Model>{
	private final Class<T> returnClass;

	public ModelManagerFactory(Class<T> returnClass){
		this.returnClass = returnClass;
	}

	public ModelManager<T> get(ConnectionProvider connProvider){
		return new ModelManager<T>( this.returnClass, connProvider );
	}

}

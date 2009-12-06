package com.mob.orm;

import com.google.common.base.*;
import com.google.common.collect.*;
import static com.mob.orm.Clauses.any;
import static com.mob.orm.Clauses.all;
import static com.mob.orm.Clauses.eq;
import static com.mob.orm.QuerySets.select;
import java.sql.*;
import javax.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.*;

public class ModelManager<T extends Model>{
	private static final Logger log = Logger.getLogger( ModelManager.class );

	private final Class<T> returnClass;
	private final ConnectionProvider connProvider;

 	public ModelManager(Class<T> returnClass, ConnectionProvider connProvider){//{{{
 		this.returnClass = returnClass;
		this.connProvider = connProvider;
 	}//}}}
	
  	public T get( Integer id ){//{{{
		try{
			T returnVal = returnClass.newInstance();
			SelectQuerySet q = select( returnVal.fields() ).where( eq( returnVal.pkField(), id ) );
			QueryExecutor qe = new QueryExecutor( this.connProvider );
			BasicResultSet results = qe.execute( q );
			if( results.next() ){
				returnVal.populateWithRS( results, 0, 0 );
				return returnVal;
			}else{
				return null; //  Not found
			}
		}catch(Exception e){
			e.printStackTrace();
			//TODO fix this
		}
		return null;
 	}//}}}

  	public List<T> get( Clause whereClause ){//{{{
		try{
			T model = returnClass.newInstance();
			SelectQuerySet q = select( model.fields() ).where( whereClause );
			QueryExecutor qe = new QueryExecutor( this.connProvider );
			BasicResultSet results = qe.execute( q );
			List<T> returnList = Lists.newArrayList();
			while( results.next() ){
				model.populateWithRS( results, 0, 0 );
				returnList.add( model );
				model = returnClass.newInstance();
			}
			return returnList;
		}catch(Exception e){
			e.printStackTrace();
			//TODO fix this
		}
		return null;
 	}//}}}

  	public List<T> get( Clause... whereClauses ){//{{{
		return get( Clauses.all( whereClauses ) );
 	}//}}}

	public List<T> all(){
		try{
			T model = returnClass.newInstance();
			SelectQuerySet q = select( model.fields() );
			QueryExecutor qe = new QueryExecutor( this.connProvider );
			BasicResultSet results = qe.execute( q );
			List<T> returnList = Lists.newArrayList();
			while( results.next() ){
				model.populateWithRS( results, 0, 0 );
				returnList.add( model );
				model = returnClass.newInstance();
			}
			return returnList;
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<T>();
			//TODO fix this
		}
	}

  	public List<T> getByIds( Integer... ids ){//{{{
		try{
			T model = returnClass.newInstance();
			Clause inClause = Clauses.in( model.pkField(), (Object[])ids );
			return get( inClause );
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
 	}//}}}

	public Integer updateGetKey(String sql, Object... args){
		QueryExecutor qe = new QueryExecutor( this.connProvider );
		return qe.updateGetKey(sql, args);
	}

	public void update(String sql, Object... args){
		QueryExecutor qe = new QueryExecutor( this.connProvider );
		qe.update(sql, args);
	}

}

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

public class KeyedModelManager<T extends SingleKeyedModel> extends ModelManager{

	private static final Logger log = Logger.getLogger( ModelManager.class );

 	public <T extends SingleKeyedModel> KeyedModelManager(Class<T> returnClass, ConnectionProvider connProvider){//{{{
		super(returnClass, connProvider);
 	}//}}}

  	public T get( Object id ){//{{{
		try{
			T returnVal = (T)this.returnClass.newInstance();
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

  	public List<T> getByIds( Object... ids ){//{{{
		try{
			T model = (T)returnClass.newInstance();
			Clause inClause = Clauses.in( model.pkField(), (Object[])ids );
			return get( inClause );
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
 	}//}}}

}


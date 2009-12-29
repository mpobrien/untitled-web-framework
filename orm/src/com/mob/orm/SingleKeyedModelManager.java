package com.mob.orm;
import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import static com.mob.orm.QuerySets.select;

public class SingleKeyedModelManager<T extends SingleKeyedModel> extends ModelManager{

	private final ClauseGenerator idClauseGen;

 	public <T extends SingleKeyedModel> SingleKeyedModelManager(Class<T> returnClass, ConnectionProvider connProvider, ClauseGenerator idClauseGen){//{{{
		super(returnClass, connProvider);
		this.idClauseGen = idClauseGen;
 	}//}}}

  	public T get( Object o ){//{{{
		try{
			T model = (T)returnClass.newInstance();
			SelectQuerySet q = select( model.fields() ).where( this.idClauseGen.make( o ) );
			QueryExecutor qe = new QueryExecutor( this.connProvider );
			BasicResultSet results = qe.execute( q );
			if( results.next() ){
				model.populateWithRS( results, 0, 0 );
				return model;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			//TODO fix this
		}
		return null;
 	}//}}}

}

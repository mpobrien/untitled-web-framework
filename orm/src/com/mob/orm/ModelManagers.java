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

public class ModelManagers{

    public <T extends Model> ModelManager<T> getManager(Class<T> returnClass, ConnectionProvider cp){
		return new ModelManager<T>(returnClass, cp);
	}

    public <T extends SingleKeyedModel> SingleKeyedModelManager<T> getSingleKeyedManager(Class<T> returnClass, ConnectionProvider cp, ClauseGenerator idClauseGen){
		return new SingleKeyedModelManager<T>(returnClass, cp, idClauseGen);
	}

    public <T extends MultiKeyedModel> MultiKeyedModelManager<T> getMultiKeyedManager(Class<T> returnClass, ConnectionProvider cp){
		return new MultiKeyedModelManager<T>(returnClass, cp);
	}


}

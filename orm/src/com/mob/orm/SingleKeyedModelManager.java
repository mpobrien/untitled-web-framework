package com.mob.orm;

public class SingleKeyedModelManager<T extends SingleKeyedModel> extends ModelManager{

 	public <T extends SingleKeyedModel> SingleKeyedModelManager(Class<T> returnClass, ConnectionProvider connProvider){//{{{
		super(returnClass, connProvider);
 	}//}}}

}

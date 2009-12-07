package com.mob.orm;

public class MultiKeyedModelManager<T extends MultiKeyedModel> extends ModelManager{

 	public <T extends MultiKeyedModel> MultiKeyedModelManager(Class<T> returnClass, ConnectionProvider connProvider){//{{{
		super(returnClass, connProvider);
 	}//}}}

}

package com.mob.orm;
import java.util.List;

public class QuerySets{

	public static SelectQuerySet select(List<DbField> fields){
		return new SelectQuerySet( fields );
	}

	public static SelectQuerySet select(DbField... fields){
		return new SelectQuerySet( fields );
	}

}


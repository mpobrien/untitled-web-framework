package com.mob.orm;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.Set;
import java.util.List;

public abstract class QuerySet{

	protected static Set<String> extractTableNames(List<DbField> fields){
		return Sets.newHashSet( Lists.transform( fields, Constants.tblNameGetter ) );
	}

	protected static List<String> extractColumnNames(List<DbField> fields){
		return Lists.transform( fields, Constants.colNameGetter );
	}
	
	
}

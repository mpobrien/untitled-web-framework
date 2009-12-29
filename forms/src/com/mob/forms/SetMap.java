package com.mob.forms;
import java.util.*;

public class SetMap<K> implements Map<K,K>{

	private final Set<K> backingSet;

	public SetMap(Set backingSet){
		this.backingSet = backingSet;
	}

	@Override
	public Set entrySet(){
		return this.backingSet;
	}

	@Override
	public Set keySet(){
		return this.backingSet;
	}

	@Override
	public Collection values(){
		return this.backingSet;
	}

	public void clear(){
		this.backingSet.clear();
	}

	public void putAll(Map map){
		throw new UnsupportedOperationException();
	}

	@Override
	public K remove(Object o){
		throw new UnsupportedOperationException();
	}

	public K put(K o1, K o2){
		this.backingSet.add( o2 );
		return o2;
	}

	public K get(Object o1){
		if( this.backingSet.contains( o1 ) ){
			return (K)o1;
		}else{
			return null;
		}
	}

	public boolean containsValue(Object o1){
		return this.backingSet.contains( o1 );
	}

	public boolean containsKey(Object o1){
		return this.backingSet.contains( o1 );
	}

	public boolean isEmpty(){
		return this.backingSet.isEmpty();
	}

	public int size(){
		return this.backingSet.size();
	}

}

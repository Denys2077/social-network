package com.denys.dw.dao;

import java.util.List;

public interface AbstractDAO <T> {

	T findEntityByTwoParameters(String paramOne, String paramTwo);
	
	T findEntityById(long id);
	
	T findEntityByObject(T obj);
	
	boolean createEntityByParameter(T param);
	
	boolean updateEntityByParameter(T param);
	
	boolean deleteEntityByParameter(T param);
	
	List<T> getAllEntities();
	
	List<T> getAllEntitiesById(long id);
}

package com.example.demo.service;

import java.util.List;

public interface BaseService<T, K> {
	T findById(K id);
	
	boolean deleteById(K id);
	
	T save(T t);
	
	List<T> list();
	
	void flush();
}

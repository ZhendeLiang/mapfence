package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.service.BaseService;

public class BaseServiceImpl<T,K,R> implements BaseService<T, K>{

	@Override
	public T findById(K id) {
		return null;
	}

	@Override
	public boolean deleteById(K id) {
		return false;
	}

	@Override
	public T save(T t) {
		return null;
	}

	@Override
	public List<T> list() {
		return null;
	}

	@Override
	public void flush() {
	}
}

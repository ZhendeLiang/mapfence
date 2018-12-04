package com.example.demo.service;

import java.util.List;

import com.example.demo.model.LandBlock;

public interface LandBlockService extends BaseService<LandBlock, String> {
	List<LandBlock> findByName(String name);

	List<LandBlock> findByLevel(int level);
}

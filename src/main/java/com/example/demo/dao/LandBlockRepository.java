package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LandBlock;

@Repository
public interface LandBlockRepository extends JpaRepository<LandBlock,String> {
	List<LandBlock> findByName(String name);
	List<LandBlock> findByLevel(int level);
	List<LandBlock> findByParentId(String parentId);
}

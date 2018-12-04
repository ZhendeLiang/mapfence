package com.example.demo.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/demo")
public class MapFenceController {
	Map<String, String[]> globalMap = new LinkedHashMap<String, String[]>();
	Map<String,String> result = new HashMap<>();
	
	
	@GetMapping(value="/mapFence/{uid}")
	public String get(@PathVariable String uid) {
		return new Gson().toJson(globalMap.get(uid));
	}
	
	@GetMapping(value="/mapFence")
	public String list(@PathVariable String uid) {
		return new Gson().toJson(globalMap);
	}
	
	@PostMapping(value="/mapFence")
	public String save(String pathArray) {
		String uid = UUID.randomUUID().toString().replaceAll("-", "");
		globalMap.put(uid, pathArray.split(","));
		result.put("result", "true");
		result.put("info", "添加成功");
		result.put("id", uid);
		System.out.println(globalMap);
		return new Gson().toJson(result);
	}

	@DeleteMapping(value="/mapFence")
	public String delete(String uid) {
		globalMap.remove(uid);
		result.put("result", "true");
		result.put("info", "删除成功");
		result.put("id", "");
		System.out.println(globalMap);
		return new Gson().toJson(result);
	}
	
	@PatchMapping(value="/mapFence")
	public String update(String uid, String pathArray) {
		globalMap.put(uid, pathArray.split(","));
		result.put("result", "true");
		result.put("info", "更新成功");
		result.put("id", uid);
		System.out.println(globalMap);
		return new Gson().toJson(result);
	}
}

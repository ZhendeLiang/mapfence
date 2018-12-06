package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.LandBlockService;
import com.google.gson.Gson;
 
@Controller
public class MapFenceController {
	@Autowired
	private LandBlockService landBlockService;

	@RequestMapping(value="/mapfence",method=RequestMethod.GET)
	public String toMapFence(ModelMap map) {
		Gson gson = new Gson();
		map.put("LandBlocks", landBlockService.list());
		map.put("LandBlockJson", gson.toJson(landBlockService.list()));
		return "mapfence";
	}
	
	@RequestMapping(value="/supermap",method=RequestMethod.GET)
	public String toSuperMap(ModelMap map) {
		Gson gson = new Gson();
		map.put("LandBlocks", landBlockService.list());
		map.put("LandBlockJson", gson.toJson(landBlockService.list()));
		return "supermap";
	}
	
	public LandBlockService getLandBlockService() {
		return landBlockService;
	}

	public void setLandBlockService(LandBlockService landBlockService) {
		this.landBlockService = landBlockService;
	}
}

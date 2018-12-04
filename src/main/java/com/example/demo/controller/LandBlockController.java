package com.example.demo.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LandBlockDto;
import com.example.demo.dto.ResultInfoDto;
import com.example.demo.exception.InOtherChildException;
import com.example.demo.exception.InOtherParentException;
import com.example.demo.exception.NotInParentException;
import com.example.demo.model.LandBlock;
import com.example.demo.service.LandBlockService;

@RestController
@RequestMapping("/landBlock")
public class LandBlockController {
	@Autowired
	private LandBlockService landBlockService;

	@RequestMapping(method=RequestMethod.POST)
	public ResultInfoDto save(@RequestBody LandBlockDto landBlockDto) {
		ResultInfoDto resultInfo = new ResultInfoDto();
		if (landBlockService.findByName(landBlockDto.getName()) == null || landBlockService.findByName(landBlockDto.getName()).size() == 0) {
			LandBlock landBlock = landBlockDto.toLandBlock();
			landBlock.setAddTime(Calendar.getInstance());
			landBlock.setModifyTime(Calendar.getInstance());
			landBlock.setHasChild(0);
			landBlock.setLevel(0);
			try {
				landBlock = landBlockService.save(landBlock);
			} catch (NotInParentException notInParentException) {
				resultInfo.setCode("2");
				resultInfo.setMsg("不在父地块内");
			} catch (InOtherChildException inOtherChildException) {
				resultInfo.setCode("3");
				resultInfo.setMsg("在其他子地块内");
			} catch (InOtherParentException inOtherParentException) {
				resultInfo.setCode("4");
				resultInfo.setMsg("在其他父地块内");
			}
		} else {
			resultInfo.setCode("1");
			resultInfo.setMsg("已有对应名称地块");
		}
		return resultInfo;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResultInfoDto delete(@PathVariable String id) {
		ResultInfoDto resultInfo = new ResultInfoDto();
		resultInfo.setData(landBlockService.deleteById(id));
		return resultInfo;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResultInfoDto get(@PathVariable String id) {
		ResultInfoDto resultInfo = new ResultInfoDto();
		resultInfo.setData(landBlockService.findById(id));
		return resultInfo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResultInfoDto list() {
		ResultInfoDto resultInfo = new ResultInfoDto();
		resultInfo.setData(landBlockService.list());
		return resultInfo;
	}
	
	public LandBlockService getLandBlockService() {
		return landBlockService;
	}

	public void setLandBlockService(LandBlockService landBlockService) {
		this.landBlockService = landBlockService;
	}
}

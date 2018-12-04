package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LandBlockRepository;
import com.example.demo.exception.InOtherChildException;
import com.example.demo.exception.InOtherParentException;
import com.example.demo.exception.NotInParentException;
import com.example.demo.model.LandBlock;
import com.example.demo.service.LandBlockService;
import com.example.demo.util.MapfenceUtil;

@Service
public class LandBlockServiceImpl implements LandBlockService{

	@Autowired
	private LandBlockRepository mapFenceRepository;
	
	@Override
	public LandBlock findById(String id) {
		return mapFenceRepository.findById(id).get();
	}

	@Override
	public boolean deleteById(String id) {
		mapFenceRepository.deleteById(id);
		return true;
	}

	@Override
	public LandBlock save(LandBlock landBlock) {
		List<LandBlock> excludeLandBlocks = null;
		//有父地块，查找所有父地块下的子地块
		if(StringUtils.isNotBlank(landBlock.getParentId())){
			Optional<LandBlock> parent = mapFenceRepository.findById(landBlock.getParentId());
			if(parent.isPresent()) {
				excludeLandBlocks = mapFenceRepository.findByParentId(parent.get().getId());
				if(excludeLandBlocks != null && excludeLandBlocks.size() > 0) {
					String [] selfLL = landBlock.getLatitudeAndLongitude().split(":");
					//在父地块内,点判断:每个点都要在父地块内
					for(String self : selfLL) {
						if(!MapfenceUtil.isInPolygon(self, parent.get().getLatitudeAndLongitude())) {
							throw new NotInParentException("不再父地块内");
						}
					}
					//不在其他子地块内,区域判断:两个区域不能有交集
					for(LandBlock excludeLandBlock : excludeLandBlocks) {
						/* 点判断
						for(String self : selfLL) {
							//如果点在其他子地块内则不能创建，问题 同边也会失败
							if(MapfenceUtil.isInPolygon(self, excludeLandBlock.getLatitudeAndLongitude())) {
								throw new InOtherChildException("在其他子地块内");
							}
						}*/
						//区域判断
						if(!MapfenceUtil.isCross(landBlock.getLatitudeAndLongitude(), excludeLandBlock.getLatitudeAndLongitude())) {
							throw new InOtherChildException("在其他子地块内");
						}
					}
				}
			}
		} else {
			excludeLandBlocks = mapFenceRepository.findByLevel(0);
			if(excludeLandBlocks != null && excludeLandBlocks.size() > 0) {
				//不在其他父地块内,区域判断:两个区域不能有交集
				for(LandBlock excludeLandBlock : excludeLandBlocks) {
					/*点判断
					String [] selfLL = landBlock.getLatitudeAndLongitude().split(":");
					for(String self : selfLL) {
						//如果点在其他父地块内则不能创建，问题 同边也会失败
						if(MapfenceUtil.isInPolygon(self, excludeLandBlock.getLatitudeAndLongitude())) {
							throw new InOtherParentException("在其他父地块内");
						}
					}*/
					//区域判断
					if(!MapfenceUtil.isCross(landBlock.getLatitudeAndLongitude(), excludeLandBlock.getLatitudeAndLongitude())) {
						throw new InOtherParentException("在其他父地块内");
					}
				}
			}
		}
		mapFenceRepository.save(landBlock);
		landBlock.setIncludeIds(landBlock.getId());
		mapFenceRepository.flush();
		return mapFenceRepository.save(landBlock);
	}

	public LandBlockRepository getMapFenceRepository() {
		return mapFenceRepository;
	}

	public void setMapFenceRepository(LandBlockRepository mapFenceRepository) {
		this.mapFenceRepository = mapFenceRepository;
	}

	@Override
	public List<LandBlock> list() {
		return mapFenceRepository.findAll();
	}

	@Override
	public void flush() {
		mapFenceRepository.flush();
	}

	@Override
	public List<LandBlock> findByName(String name) {
		return mapFenceRepository.findByName(name);
	}

	@Override
	public List<LandBlock> findByLevel(int level) {
		return mapFenceRepository.findByLevel(level);
	}
}

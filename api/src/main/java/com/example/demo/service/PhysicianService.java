package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.demo.entity.PhysicianEntity;
import com.example.demo.entity.PositionEntity;
import com.example.demo.entity.OfficeEntity;
import com.example.demo.exception.NotFoundException;

public interface PhysicianService {
	
	Integer count(Map<String, Object> filter);

	List<PhysicianEntity> fetch(Map<String, Object> filter);

	public Integer create(PhysicianEntity physician);

	public Optional<PhysicianEntity> fetch(Integer id);

	public Integer remove(PhysicianEntity physician);

	public Integer update(PhysicianEntity physician);
	
	public List<OfficeEntity> fetchOffices(Integer physicianId);

	public void updateOffices(Integer physicianId, Set<Integer> OfficeId) throws NotFoundException;

// 医师职位设置
	public List<PositionEntity> fetchPositions(Integer physicianId);

	public void updatePositions(Integer physicianId, List<Integer> positionIds) throws NotFoundException;
}



package com.example.demo.service.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PhysicianEntity;
import com.example.demo.entity.PositionEntity;
import com.example.demo.entity.OfficeEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.PhysicianMapper;
import com.example.demo.mapper.PhysicianPositionMapper;
import com.example.demo.mapper.PhysicianOfficeMapper;
import com.example.demo.service.PhysicianService;

@Service
public class PhysicianServiceImpl implements PhysicianService {

    private final PhysicianMapper physicianMapper;

    private final PhysicianPositionMapper physicianPositionMapper;
    
    private final PhysicianOfficeMapper physicianOfficeMapper;

    public PhysicianServiceImpl(
        PhysicianMapper physicianMapper,
        PhysicianPositionMapper physicianPositionMapper,
        PhysicianOfficeMapper physicianOfficeMapper
    ){
        this.physicianMapper = physicianMapper;
        this.physicianPositionMapper = physicianPositionMapper;
        this.physicianOfficeMapper = physicianOfficeMapper;
    }

    @Override
    public Integer count(Map<String, Object> filter){
        return this.physicianMapper.count(filter);
    }

    @Override
    public List<PhysicianEntity> fetch(Map<String, Object> filter){
        return this.physicianMapper.find(filter);
    }

    @Override
    public Integer create(PhysicianEntity physician) {
        return this.physicianMapper.create(physician);
    }

    @Override
    public Optional<PhysicianEntity> fetch(Integer id) {
        return Optional.ofNullable(
            this.physicianMapper.findById(id)
        );
    }
    
    @Override
    public Integer remove(PhysicianEntity physician) {
        return this.physicianMapper.remove(physician);
    }

    @Override
    public Integer update(PhysicianEntity physician) {
        return this.physicianMapper.update(physician);
    }

    // 左连接
    @Override
    public List<OfficeEntity> fetchOffices(Integer physicianId) {
        return this.physicianOfficeMapper.findByPhysicianId(physicianId);
    }

    @Override
    @Transactional
    public void updateOffices(
        Integer physicianId, Set<Integer> newOfficeId
    ) throws NotFoundException {
        // 删除旧
        Iterator<OfficeEntity> oldOfficesIterator = this.fetchOffices(physicianId).iterator();
        while (oldOfficesIterator.hasNext()) {
            Integer oldOfficeId = oldOfficesIterator.next().getId();
            this.physicianOfficeMapper.remove(physicianId, oldOfficeId);
        }
        // 插入新
        Iterator<Integer> newOfficesIterator = newOfficeId.iterator();
        while (newOfficesIterator.hasNext()) {
            Integer newOfficeIdValue = newOfficesIterator.next();
            this.physicianOfficeMapper.create(physicianId, newOfficeIdValue);
        }
    }

    @Override
    public List<PositionEntity> fetchPositions(Integer physicianId) {
        return this.physicianPositionMapper.findByPhysicianId(physicianId);
    }

    @Override
    @Transactional
    public void updatePositions(
        Integer physicianId, List<Integer> newPositionIds
    ) throws NotFoundException {
        // 删除旧职位
        Iterator<PositionEntity> oldPositionsIterator = this.fetchPositions(physicianId).iterator();
        while (oldPositionsIterator.hasNext()) {
            Integer oldPositionId = oldPositionsIterator.next().getId();
            this.physicianPositionMapper.remove(physicianId, oldPositionId);
        }
        // 插入新职位
        Iterator<Integer> newPositionsIterator = newPositionIds.iterator();
        while (newPositionsIterator.hasNext()) {
            Integer newPositionId = newPositionsIterator.next();
            this.physicianPositionMapper.create(physicianId, newPositionId);
        }
    }
}

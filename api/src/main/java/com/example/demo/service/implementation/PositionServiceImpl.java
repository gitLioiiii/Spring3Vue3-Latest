package com.example.demo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PositionEntity;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{
    
    private final PositionMapper positionMapper;

    public PositionServiceImpl(
        PositionMapper positionMapper
    ) {
        this.positionMapper = positionMapper;
    }

    @Override
    public List<PositionEntity> fetch() {
        return this.positionMapper.find();
    }

    @Override
    public Optional<PositionEntity> fetch(Integer id) {
        return Optional.ofNullable(
            this.positionMapper.findById(id)
        );
    }

    @Override
    public Integer create(PositionEntity position) {
        return this.positionMapper.create(position);
    }

    @Override
    public Integer update(PositionEntity position) {
        return this.positionMapper.update(position);
    }

    @Override
    public Integer remove(PositionEntity position) {
        return this.positionMapper.remove(position);
    }
}

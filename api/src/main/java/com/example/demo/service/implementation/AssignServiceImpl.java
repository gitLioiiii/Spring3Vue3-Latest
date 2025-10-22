package com.example.demo.service.implementation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PhysicianEntity;
import com.example.demo.mapper.AssignMapper;
import com.example.demo.service.AssignService;

@Service
public class AssignServiceImpl implements AssignService {

    private final AssignMapper assignMapper;

    public AssignServiceImpl(AssignMapper assignMapper) {
        this.assignMapper = assignMapper;
    }

    @Override
    public Integer count(Map<String, Object> filter) {
        return this.assignMapper.count(filter);
    }

    @Override
    public List<PhysicianEntity> fetch(Map<String, Object> filter) {
        return this.assignMapper.find(filter);
    }
}



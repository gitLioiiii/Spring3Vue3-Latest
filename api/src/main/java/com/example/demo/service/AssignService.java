package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.PhysicianEntity;

public interface AssignService {

    Integer count(Map<String,Object> filter);

    List<PhysicianEntity> fetch(Map<String,Object> filter);
}



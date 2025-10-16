package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.PositionEntity;

public interface PositionService {

    public List<PositionEntity> fetch();

    public Optional<PositionEntity> fetch(Integer id);

    public Integer create(PositionEntity position);

    public Integer update(PositionEntity position);

    public Integer remove(PositionEntity position);
}

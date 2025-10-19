package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.OfficeEntity;

public interface OfficeService {

    public List<OfficeEntity> fetch();

    public Optional<OfficeEntity> fetch(Integer id);

    public Integer create(OfficeEntity office);

    public Integer update(OfficeEntity office);

    public Integer remove(OfficeEntity office);
}

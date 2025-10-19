package com.example.demo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.OfficeEntity;
import com.example.demo.mapper.OfficeMapper;
import com.example.demo.service.OfficeService;

@Service
public class OfficeServiceImpl implements OfficeService{

    private final OfficeMapper officeMapper;

    public OfficeServiceImpl(
        OfficeMapper officeMapper
    ) {
        this.officeMapper = officeMapper;
    }

    @Override
    public List<OfficeEntity> fetch() {
        return this.officeMapper.find();
    }

    @Override
    public Optional<OfficeEntity> fetch(Integer id) {
        return Optional.ofNullable(
            this.officeMapper.findById(id)
        );
    }

    @Override
    public Integer create(OfficeEntity office) {
        return this.officeMapper.create(office);
    }

    @Override
    public Integer update(OfficeEntity office) {
        return this.officeMapper.update(office);
    }

    @Override
    public Integer remove(OfficeEntity office) {
        return this.officeMapper.remove(office);
    }
}

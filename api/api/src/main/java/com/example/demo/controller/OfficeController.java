package com.example.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OfficeEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.OfficeService;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.OfficeValidateGroup;

@RestController
@RequestMapping("/office")
public class OfficeController {
    
    private final OfficeService officeService;

    public OfficeController(
        OfficeService officeService
    ) {
        this.officeService = officeService;
    }

// 对应前端/office/all
    @GetMapping("/all")
    public ResultTemplate index() {
        ResultTemplate result   = new ResultTemplate();

        result.putPayload("office", this.officeService.fetch());

        return result;
    }

    @PostMapping("/create")
    public ResultTemplate create(
        @RequestBody @Validated(OfficeValidateGroup.Create.class) OfficeEntity office, 
        BindingResult bindingResult
    ) throws ValidateFailedException {
        ResultTemplate result = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        this.officeService.create(office);

        return result;
    }

    @PostMapping("/update")
    public ResultTemplate update(
        @RequestBody @Validated(OfficeValidateGroup.Update.class) OfficeEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }
        
        OfficeEntity office  = this.officeService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        office.setName(fields.getName());
        office.setDescription(fields.getDescription());

        this.officeService.update(office);

        return result;
    }

    @PostMapping("/remove")
    public ResultTemplate remove(
        @RequestBody @Validated(OfficeValidateGroup.Remove.class) OfficeEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }
        
        OfficeEntity  office  = this.officeService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        this.officeService.remove(office);

        return result;
    }

    @GetMapping("/{id}")
    public ResultTemplate fetch(
        @PathVariable("id") Integer id
    ) throws NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        OfficeEntity office = this.officeService.fetch(id).orElseThrow(
            () -> new NotFoundException()
        );
        result.putPayload("office", office);

        return result;
    }
}

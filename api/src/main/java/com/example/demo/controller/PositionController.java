package com.example.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PositionEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.PositionService;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.PositionValidateGroup;

@RestController
@RequestMapping("/position")
public class PositionController {

    private final PositionService positionService;

    public PositionController(
        PositionService positionService
    ) {
        this.positionService = positionService;
    }
    
    @GetMapping("/all")
    public ResultTemplate index() {
        ResultTemplate result   = new ResultTemplate();

        result.putPayload("position", this.positionService.fetch());

        return result;
    }

    @PostMapping("/create")
    public ResultTemplate create(
        @RequestBody @Validated(PositionValidateGroup.Create.class) PositionEntity position, 
        BindingResult bindingResult
    ) throws ValidateFailedException {
        ResultTemplate result = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        this.positionService.create(position);

        return result;
    }

    @PostMapping("/update")
    public ResultTemplate update(
        @RequestBody @Validated(PositionValidateGroup.Update.class) PositionEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }
        
        PositionEntity position  = this.positionService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        position.setName(fields.getName());
        position.setDescription(fields.getDescription());

        this.positionService.update(position);

        return result;
    }

    @PostMapping("/remove")
    public ResultTemplate remove(
        @RequestBody @Validated(PositionValidateGroup.Remove.class) PositionEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }
        
        PositionEntity position   = this.positionService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        this.positionService.remove(position);

        return result;
    }

    @GetMapping("/{id}")
    public ResultTemplate fetch(
        @PathVariable("id") Integer id
    ) throws NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        PositionEntity position = this.positionService.fetch(id).orElseThrow(
            () -> new NotFoundException()
        );
        result.putPayload("position", position);

        return result;
    }
}

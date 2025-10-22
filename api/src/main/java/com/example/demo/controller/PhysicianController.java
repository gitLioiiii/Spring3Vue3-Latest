package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PhysicianEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.PhysicianService;
import com.example.demo.utils.Pagination;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.PhysitionValidateGroup;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



// @CrossOrigin
@RestController
@RequestMapping("/physician")
public class PhysicianController {
    
    private final PhysicianService physicianService;
	private final PasswordEncoder passwordEncoder;
	public PhysicianController(
		PhysicianService physicianService,
        PasswordEncoder passwordEncoder
	) {
		this.physicianService = physicianService;
        this.passwordEncoder    = passwordEncoder;
	}

    @GetMapping("")
    public ResultTemplate index(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Integer officeId
        ) {
            ResultTemplate result = new ResultTemplate();
            Map<String, Object> filter = new HashMap<>();

        if (keywords != null) {
            filter.put("keywords", keywords);
        }

        if (officeId != null) {
            filter.put("officeId", officeId);
        }

        Integer total       = this.physicianService.count(filter);

        Pagination pagination   = Pagination.paginate(total, pageSize, page);

        filter.put("offset", pagination.getOffset());
        filter.put("limit", pagination.getLimit());
        result.putPayload("physicians", this.physicianService.fetch(filter));

        result.putPayload("pagination", pagination);
        
        return result;
    }
    
    @PostMapping("/create")
    public ResultTemplate create(
        @RequestBody @Validated(PhysitionValidateGroup.Create.class) PhysicianEntity physician,
        BindingResult bindingResult
    ) throws ValidateFailedException {
        ResultTemplate result = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        physician.setPassword(
            this.passwordEncoder.encode(physician.getPassword())
        );
        this.physicianService.create(physician);

        return result;
    }

    @GetMapping("/{id}")
    public ResultTemplate fetch(
        @PathVariable Integer id
    ) throws NotFoundException {
        ResultTemplate result = new ResultTemplate();

        PhysicianEntity physician = this.physicianService.fetch(id).orElseThrow(
            () -> new NotFoundException()
        );

        result.putPayload("physician", physician);

        return result;
    }
    
    
    @PostMapping("/remove")
    public ResultTemplate remove(
        @RequestBody @Validated(PhysitionValidateGroup.Remove.class) PhysicianEntity fields,
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result = new ResultTemplate();
        
        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        PhysicianEntity physician = this.physicianService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        physician.setDeletedAt(LocalDateTime.now());
        this.physicianService.remove(physician);

        return result;
    }

    @PostMapping("/update")
    public ResultTemplate update(
    @RequestBody @Validated(PhysitionValidateGroup.Update.class) PhysicianEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        PhysicianEntity physician = this.physicianService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        physician.setUsername(fields.getUsername());
        // physician.setPassword(fields.getPassword());
        physician.setName(fields.getName());
        physician.setAge(fields.getAge());
        physician.setGender(fields.getGender());
        physician.setOfficeId(fields.getOfficeId());
        physician.setPhone(fields.getPhone());
        physician.setServe(fields.getServe());

        this.physicianService.update(physician);

        return result;
    }


//医师职位设置
    @GetMapping("/{id}/positions")
    public ResultTemplate fetchPositions(
        @PathVariable("id") Integer id
    ) {
        ResultTemplate result = new ResultTemplate();

        result.putPayload("positions", this.physicianService.fetchPositions(id));

        return result;
    }

    @PostMapping("/{id}/positions")
    public ResultTemplate updatePositions(
        @PathVariable("id") Integer id, 
        @RequestParam("positionsId") List<Integer> positionsId
    ) {
        ResultTemplate result = new ResultTemplate();

        try {
            this.physicianService.updatePositions(id, positionsId);
        } catch(Exception exception) {
            result.setStatus(false).setMessage("SAVE_FAILED");
        }

        return result;
    }
}

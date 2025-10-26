package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AssignService;
import com.example.demo.utils.Pagination;
import com.example.demo.utils.ResultTemplate;

@RestController
@RequestMapping("/assign")
public class AssignController {

    private final AssignService assignService;

    public AssignController(AssignService assignService) {
        this.assignService = assignService;
    }

    @GetMapping("/view")
    public ResultTemplate index(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        ResultTemplate result = new ResultTemplate();

        Map<String, Object> filter = new HashMap<>();

        Integer total = this.assignService.count(filter);
        Pagination pagination = Pagination.paginate(total, pageSize, page);

        filter.put("offset", pagination.getOffset());
        filter.put("limit", pagination.getLimit());

        result.putPayload("position", this.assignService.fetch(filter));
        result.putPayload("pagination", pagination);

        return result;
    }
}



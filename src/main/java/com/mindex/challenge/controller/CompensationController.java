package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation.getEmployee().getEmployeeId());
        if(compensation.getEmployee().getEmployeeId()==null||employeeService.read(compensation.getEmployee().getEmployeeId())==null){
            compensation.setEmployee(employeeService.create(compensation.getEmployee()));
        }

        return compensationService.create(compensation);
    }
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received employee compensation request for id [{}]", id);
        return compensationService.read(id);
    }


}

package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String employeeUrl;
    private String employeeIdUrl;
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;
    @Autowired
    private EmployeeService employeeService;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setup() {
        compensationService = new CompensationServiceImpl();
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
        Compensation testCompensation;
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(null);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testCompensation = new Compensation(testEmployee,"$100,000", "today");


        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployee());
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertCompensationEquivalence(createdCompensation,readCompensation);
    }


    private static void assertCompensationEquivalence(Compensation expected, Compensation actual){//equivalence makes sure id is different since it is set by the server
        assertEquals(expected.getEmployee().getEmployeeId(),expected.getEmployee().getEmployeeId());
        assertEquals(expected.getEmployee().getDepartment(),actual.getEmployee().getDepartment());
        assertEquals(expected.getEmployee().getPosition(),actual.getEmployee().getPosition());
        assertEquals(expected.getSalary(),actual.getSalary());
        assertEquals(expected.getEffectiveDate(),actual.getEffectiveDate());
    }
}

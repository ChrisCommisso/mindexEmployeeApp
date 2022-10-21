package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String employeeUrl;
    private String employeeIdUrl;
    private String reportingStructureUrl;

    private Employee testEmployee;
    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        reportingStructureUrl = "http://localhost:" + port + "/reportingstructure/{id}";
    }

    @Test
    public void testRead() {
        testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        Employee testReport2 = new Employee();
        testReport2.setFirstName("John");
        testReport2.setLastName("Doe");
        testReport2.setDepartment("Engineering");
        testReport2.setPosition("Developer");
        testReport2.setDirectReports(Arrays.asList(new Employee[]{}));
        Employee testReport = new Employee();
        testReport.setFirstName("John");
        testReport.setLastName("Doe");
        testReport.setDepartment("Engineering");
        testReport.setPosition("Developer");
        testReport.setDirectReports(Arrays.asList(new Employee[]{testReport2}));
        testEmployee.setDirectReports(Arrays.asList(new Employee[]{testReport}));
        // Create checks
        restTemplate.postForEntity(employeeUrl, testReport, Employee.class).getBody();
        restTemplate.postForEntity(employeeUrl, testReport2, Employee.class).getBody();
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);
        String id = readEmployee.getEmployeeId();
        ReportingStructure createdStructure = restTemplate.getForEntity( reportingStructureUrl, ReportingStructure.class, readEmployee.getEmployeeId()).getBody();
        assertNotNull(createdStructure);
        assertTrue(createdStructure.getNumberOfReports()==2);

    }
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}

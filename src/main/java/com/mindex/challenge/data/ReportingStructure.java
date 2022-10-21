package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
    int getReports(Employee e){
        int reportsOutput = 0;
        for (Employee report:e.getDirectReports()) {
            reportsOutput++;
            List<Employee> directReports = report.getDirectReports();
            if(directReports.stream().count()>0){
                reportsOutput+=getReports(report);
            }
        }
        return reportsOutput;
    }
    Employee employee;
    int numberOfReports;
    public ReportingStructure(){
    }
    public ReportingStructure(Employee employee){//had to make this a copy constructor because employee was null otherwise in the test suite
        this.employee = new Employee();
        this.employee.setPosition(employee.getPosition());
        this.employee.setDirectReports(employee.getDirectReports());
        this.employee.setEmployeeId(employee.getEmployeeId());
        this.employee.setDepartment(employee.getDepartment());
        this.employee.setFirstName(employee.getFirstName());
        this.employee.setLastName(employee.getLastName());
        numberOfReports=getReports(employee);
    }
    public int getNumberOfReports(){
        return numberOfReports;
    }
}

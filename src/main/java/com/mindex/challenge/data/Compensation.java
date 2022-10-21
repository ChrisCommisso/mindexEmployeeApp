package com.mindex.challenge.data;


public class Compensation {

    Employee employee;
    String salary;
    String effectiveDate;

    public Compensation(Employee employee, String salary, String effectiveDate) {
        this.employee=employee;
        this.salary=salary;
        this.effectiveDate=effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}

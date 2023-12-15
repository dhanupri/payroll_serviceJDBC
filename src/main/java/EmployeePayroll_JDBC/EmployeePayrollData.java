package EmployeePayroll_JDBC;

import java.time.LocalDate;
import java.util.Date;

public class EmployeePayrollData {
    public int id;
    public String name;
    public int salary;
    public LocalDate startDate;
    public Date date;

    public EmployeePayrollData() {
        this.id=id;
        this.name=name;
        this.salary=salary;
        this.startDate=startDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return (int) salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public EmployeePayrollData(Integer id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    public EmployeePayrollData(int id, String name, int salary, Date date){
        this(id, name, salary);
        this.date=date;
    }
    public EmployeePayrollData(int id, String name, int salary, LocalDate startDate){
        this(id, name, salary);
        this.startDate = startDate;
    }
    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id &&
                Double.compare(that.salary, salary) == 0 &&
                name.equals(that.name);
    }
}
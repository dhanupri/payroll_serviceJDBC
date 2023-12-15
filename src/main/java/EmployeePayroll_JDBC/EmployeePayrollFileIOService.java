package EmployeePayroll_JDBC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
public class EmployeePayrollFileIOService {
    public void writeData(List<EmployeePayrollData> employeePayrollList) {
    }
    long entryCount = 0;
    public long countEntries() {
        entryCount++;
        return entryCount;
    }
    public void printData() {
    }
}

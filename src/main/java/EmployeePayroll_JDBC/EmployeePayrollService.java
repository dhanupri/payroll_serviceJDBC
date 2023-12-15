package EmployeePayroll_JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class EmployeePayrollService {
    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<EmployeePayrollData> employeePayrollList;
    //constructor
    private EmployeePayrollDBService employeePayrollDBService;
    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }
    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this();
        this.employeePayrollList = employeePayrollList;
    }
    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
    }
    //write employee payroll
    public void writeEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO)){
            System.out.println("Writing employee payroll roaster to console"+employeePayrollList);
        }
        else if(ioService.equals(IOService.FILE_IO)){
            new EmployeePayrollFileIOService().writeData(employeePayrollList);
        }
    }
    //read employeepayroll
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollList = employeePayrollDBService.readData();
        return this.employeePayrollList;
    }
    //print the data
    public void printData(IOService ioService) throws IOException {
        if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().printData();
    }
    // to calculate number of entities
    public long countEntries(IOService ioService) throws IOException {
        if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().countEntries();
        return 0;

    }
    //read the data
    public void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee Salary: ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }
    //update employee data
    public void updateEmployeedata(String name,int amt) throws SQLException {
        int result=new EmployeePayrollDBService().updateEmployeedata(name,amt);
    }
    public List<EmployeePayrollData> readEmployeePayroll(IOService ioService){
        if(ioService.equals(IOService.DB_IO)){
            this.employeePayrollList = employeePayrollDBService.readData();
        }
        return this.employeePayrollList;
    }
    //update salary
    public void updateSalary(String name, int amt) throws SQLException {
        int res=employeePayrollDBService.updateEmployeedata(name,amt);
        if(res==0){
            return;
        }
        EmployeePayrollData employeePayrollData=this.getEmployeePayrollData(name);
        if(employeePayrollData!=null){
            employeePayrollData.salary= amt;
        }
    }
    public EmployeePayrollData getEmployeePayrollData(String name){
        EmployeePayrollData employeePayrollData;
        employeePayrollData=this.employeePayrollList.stream()
                .filter(employeePayrollItem ->employeePayrollItem.name.equals(name))
                .findFirst().orElse(null);
        return employeePayrollData;
    }
    public boolean checkEmployeePayrollSyncDB(String name){
      List<EmployeePayrollData> employeePayrollDataList=employeePayrollDBService.getEmployeePayrollData(name);
      return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }
}

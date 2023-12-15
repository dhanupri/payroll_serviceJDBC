package EmployeePayroll_JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
public class EmployeePayrollService {
    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<EmployeePayrollData> employeePayrollList;
//    LocalDate date=java.time.LocalDate.now();
    //constructor
    private EmployeePayrollDBService employeePayrollDBService;
    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }
    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this();
        this.employeePayrollList = employeePayrollList;
    }
    public static void main(String[] args) throws SQLException, ParseException {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        System.out.println("1.insert values"+
                "2.retrive all data"+
                "3.retrive based on specific condition :(yes/no)");
        int res=consoleInputReader.nextInt();
        switch (res){
            case 1:
                employeePayrollService.readEmployeePayrollData(consoleInputReader);
                break;
            case 2:
                EmployeePayrollDBService.retrive_display();
                break;
            case 3:
                System.out.println("1.based on name"+
                        "2.based on date");
                int n=consoleInputReader.nextInt();
                switch (n){
                    case 1:
                        System.out.println("Enter the name :");
                        String name=consoleInputReader.next();
                        List<EmployeePayrollData> data=EmployeePayrollDBService.retrive_By_name(name);
                        for (EmployeePayrollData emp1:data){
                            System.out.println("ID:"+emp1.id+",Name:"+emp1.name+",salary:"+emp1.salary+",date"+emp1.startDate);
                        }

                        break;
                    case 2:
                        System.out.println("Enter the date");
                        String date=consoleInputReader.next();
                        EmployeePayrollDBService.retrive_between_range(java.sql.Date.valueOf(LocalDate.parse(date)));
                        break;
                }

        }

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
    public void readEmployeePayrollData(Scanner consoleInputReader) throws SQLException, ParseException {

        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee Salary: ");
        int salary = consoleInputReader.nextInt();
        System.out.println("Enter the date:");
        String inputDate = consoleInputReader.next();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date=dateFormat.parse(inputDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        EmployeePayrollData emp = new EmployeePayrollData(id, name, salary, LocalDate.parse(inputDate));
        employeePayrollList.add(emp);
        new EmployeePayrollDBService().insert(emp);
//        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
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

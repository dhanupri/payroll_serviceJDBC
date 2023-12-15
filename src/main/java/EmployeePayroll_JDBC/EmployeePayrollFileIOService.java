package EmployeePayroll_JDBC;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class EmployeePayrollFileIOService {
    public static String PAYROLL_FILE_NAME="payroll-file.txt";
    //writing employee into file
    public void writeData(List<EmployeePayrollData> employeePayrollList) {
        StringBuffer stringBuffer=new StringBuffer();
        employeePayrollList.forEach(employeeData ->{
            String employeeDataString =employeeData.toString().concat("\n");
            stringBuffer.append(employeeDataString);
        });
        try{
            Files.write(Paths.get(PAYROLL_FILE_NAME),stringBuffer.toString().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //count the number of entries
    public long countEntries() throws IOException {
        long entries=0;
        entries=Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
        return entries;
    }
    //print the data
    public void printData() throws IOException {
        Files.lines(new File(PAYROLL_FILE_NAME).toPath()).forEach(System.out::println);
    }
}

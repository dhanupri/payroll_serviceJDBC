import EmployeePayroll_JDBC.EmployeePayrollDBService;
import EmployeePayroll_JDBC.EmployeePayrollData;
import EmployeePayroll_JDBC.EmployeePayrollService;
import EmployeePayroll_JDBC.sql_con;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.DB_IO;
import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.FILE_IO;
import static org.junit.Assert.assertTrue;

public class EmployeePayrollServiceTest {
    @Test
    public void EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() throws IOException {
        EmployeePayrollData[] arraysOfEmployees = {
                new EmployeePayrollData(1, "Jeff Bezos", 100000),
                new EmployeePayrollData(2, "Bill Gates", 200000),
                new EmployeePayrollData(3, "Elon Mask", 300000),
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        employeePayrollService.printData(FILE_IO);
        long entries=arraysOfEmployees.length;
        System.out.println(entries);
        Assert.assertEquals(3, entries );
    }
    //uc2
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        Assert.assertEquals(4, employeePayrollData.size());
    }
    //uc3
    @Test
    public void salary_syncwithDB() throws SQLException {
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.updateSalary("Terisa",3000000);
        boolean result=employeePayrollService.checkEmployeePayrollSyncDB("Terisa");
        assertTrue(result);
    }
    //uc7
    @Test
    public void testAddEmployeeToPayroll() throws SQLException {
        LocalDate localDate = LocalDate.of(2023, 12, 17);
        EmployeePayrollData employeePayrollData=new EmployeePayrollData(19,"nishanth",300000,localDate ,"M");
        EmployeePayrollDBService.insert(employeePayrollData);
        assertTrue(compareEmployeeWithDatabase(String.valueOf(employeePayrollData)));
    }
    public static boolean compareEmployeeWithDatabase(String name){
        List<EmployeePayrollData> data=new ArrayList<>();
        EmployeePayrollData data1=new EmployeePayrollData();
        try {
            Connection connection= sql_con.getCon();
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM employee_payroll WHERE name=?");
            ps.setString(1,name);
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next()){
                data1.setSalary(resultSet.getInt("salary"));
                data.add(data1);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  data.isEmpty();
    }
}




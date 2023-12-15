import EmployeePayroll_JDBC.EmployeePayrollData;
import EmployeePayroll_JDBC.EmployeePayrollService;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.DB_IO;
import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.FILE_IO;
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
        Assert.assertTrue(result);
    }
}




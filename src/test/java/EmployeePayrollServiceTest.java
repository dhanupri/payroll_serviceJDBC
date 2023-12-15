
import EmployeePayroll_JDBC.EmployeePayrollData;
import EmployeePayroll_JDBC.EmployeePayrollService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.DB_IO;
import static EmployeePayroll_JDBC.EmployeePayrollService.IOService.FILE_IO;


public class EmployeePayrollServiceTest {

    @Test
    public void EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arraysOfEmployees = {
                new EmployeePayrollData(1, "Jeff Bezos", 100000.0),
                new EmployeePayrollData(2, "Bill Gates", 200000.0),
                new EmployeePayrollData(3, "Elon Mask", 300000.0),
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        employeePayrollService.printData(FILE_IO);
        long entries=arraysOfEmployees.length;
        System.out.println(entries);
        Assert.assertEquals(3, entries );
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        Assert.assertEquals(4, employeePayrollData.size());
    }
}




package EmployeePayroll_JDBC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class EmployeePayrollDBService {
    private PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;
    public EmployeePayrollDBService(){
    }
    public static EmployeePayrollDBService getInstance(){
        if(employeePayrollDBService == null){
            employeePayrollDBService=new EmployeePayrollDBService();
        }
        return employeePayrollDBService;
    }
    //read data
    public List<EmployeePayrollData> readData(){
        //uc4
        String sql="SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            Connection connection=sql_con.getCon();
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery(sql);
            while (result.next()){
                int id=result.getInt("id");
                String name=result.getString("name");
                int salary=result.getInt("salary");
                LocalDate startDate=result.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,salary,startDate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeePayrollList;
    }
    public List<EmployeePayrollData> getEmployeePayrollData(String name){
        List<EmployeePayrollData> employeePayrollDataList=null;
        if(this.employeePayrollDataStatement==null){
            this.prepareStatementForEmployeePayrollData();
            try {
                employeePayrollDataStatement.setString(1,name);
                ResultSet resultSet=employeePayrollDataStatement.executeQuery();
                employeePayrollDataList=this.getEmployeePayrollData(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return employeePayrollDataList;
    }
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try{
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    //insert values into table
    public void  insert(EmployeePayrollData emp) throws SQLException {
        Connection connection=sql_con.getCon();
        PreparedStatement ps=connection.prepareStatement("INSERT INTO employee_payroll VALUES(?,?,?,?)");
        ps.setInt(1, emp.getId());
        ps.setString(2, emp.getName());
        ps.setInt(3,emp.getSalary());
        LocalDate d=emp.getStartDate();
        ps.setDate(4, Date.valueOf(d));
        int resultSet=ps.executeUpdate();
        connection.close();

    }
    //retrive based on name
    public static List<EmployeePayrollData> retrive_By_name(String name){
        List<EmployeePayrollData> data=new ArrayList<>();
        EmployeePayrollData data1=new EmployeePayrollData();
        try {
            Connection connection=sql_con.getCon();
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
        return data;
    }
    //retrive the employee payroll data between an range
    public static void retrive_between_range(Date date){
        List<EmployeePayrollData> data=new ArrayList<>();
        EmployeePayrollData data1=new EmployeePayrollData();
        try {
            Connection connection=sql_con.getCon();
            assert connection != null;
            PreparedStatement ps=connection.prepareStatement("SELECT * FROM employee_payroll WHERE start BETWEEN CAST(? AS DATE) AND DATE(NOW())");
            ps.setString(1, String.valueOf(date));
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int salary=resultSet.getInt("salary");
                java.util.Date date1=resultSet.getDate("start");
                System.out.println("ID:"+id+",Name:"+name+",salary:"+salary+",Date:"+date1);
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //using prepared statement
    private void prepareStatementForEmployeePayrollData() {
    try {
        Connection connection=sql_con.getCon();
        String sql="SELECT * FROM employee_payroll WHERE name=?;";
        employeePayrollDataStatement=connection.prepareStatement(sql);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    //prepared statement
    public int updateEmployeedata(String name, int amt) throws SQLException {
        return this.updateEmployeedataUsingStatement(name,amt);
    }
    //normal statement
    private int updateEmployeedataUsingStatement(String name,int salary) throws SQLException {
        String sql=String.format("update employee_payroll set salary=%d where name='%s';",salary,name);
        try (Connection connection=sql_con.getCon()){
            Statement statement=connection.createStatement();
            return statement.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static void retrive_display() {
        //uc4 retrive all rows from table
        try {
            Connection connection = sql_con.getCon();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employee_payroll;");
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int salary=resultSet.getInt("salary");
                java.util.Date date=resultSet.getDate("start");
               System.out.println("ID:"+id+",Name:"+name+",salary:"+salary+",Date:"+date);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
//uc6  aggregate function
    public static void aggregate_function(String gender) {
        String male;
        String female;
        try {
            Connection connection = sql_con.getCon();
            PreparedStatement ps = connection.prepareStatement("SELECT ?,SUM(salary) AS total_salary,AVG(salary) AS average_salary, MIN(salary) AS min_salary, MAX(salary) AS max_salary, COUNT(*) AS employee_count FROM employee_payroll GROUP BY gender");
            ps.setString(1,gender);
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next()){
                double totalSalary = resultSet.getDouble("total_salary");
                double averageSalary = resultSet.getDouble("average_salary");
                double minSalary = resultSet.getDouble("min_salary");
                double maxSalary = resultSet.getDouble("max_salary");
                int employeeCount = resultSet.getInt("employee_count");
                System.out.println("Gender: " + gender + ", Total Salary: " + totalSalary + ", Average Salary: " + averageSalary + ", Min Salary: " + minSalary + ", Max Salary: " + maxSalary + ", Employee Count: " + employeeCount);

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



}



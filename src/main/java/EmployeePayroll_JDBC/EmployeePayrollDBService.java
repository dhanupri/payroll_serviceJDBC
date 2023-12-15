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
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll_service_?useSSL=false";
        String userName = "root";
        String password = "Rohitsharma45";
        Connection connection;
        System.out.println("Connecting to database : " +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful!!!" + connection);
        return connection;
    }
    //read data
    public List<EmployeePayrollData> readData(){
        //uc2
        String sql="SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            Connection connection=this.getConnection();
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
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    private void prepareStatementForEmployeePayrollData() {
    try {
        Connection connection=this.getConnection();
        String sql="SELECT * FROM employee_payroll WHERE name=?;";
        employeePayrollDataStatement=connection.prepareStatement(sql);

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
    //prepared statement
    public int updateEmployeedata(String name, int amt) throws SQLException {
        return this.updateEmployeedataUsingStatement(name,amt);

    }
    //normal statement
    private int updateEmployeedataUsingStatement(String name,int salary) throws SQLException {
        String sql=String.format("update employee_payroll set salary=%d where name='%s';",salary,name);
        try (Connection connection=this.getConnection()){
            Statement statement=connection.createStatement();
            return statement.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}


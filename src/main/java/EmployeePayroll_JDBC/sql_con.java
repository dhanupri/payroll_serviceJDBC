package EmployeePayroll_JDBC;


import java.sql.*;
public class sql_con {
    public static Connection getCon(){
        Connection con=null;
        try{
            String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll_service_?useSSL=false";
            String userName = "root";
            String password = "Rohitsharma45";
            Connection connection;
            System.out.println("Connecting to database : " +jdbcURL);
            connection = DriverManager.getConnection(jdbcURL,userName,password);
            System.out.println("Connection is successful!!!" + connection);
            return connection;
        }
        catch(Exception e){System.out.println(e);}
        return con;
    }
}

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;

public class Payroll_serviceDB {
    public static void main(String[] args){
        String jdbcURL="jdbc:mysql://localhost:3306/employee_payroll_service?useSSL=false";
        String useName="root";
        String password="Rohitsharma45";
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can't find the driver",e);
        }
        listDrivers();
        //connection with database
        try {
            System.out.println("Connecting to database:"+jdbcURL);
            connection=DriverManager.getConnection(jdbcURL,useName,password);
            System.out.println("Connection is sucessful"+connection);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    private static void listDrivers(){
        Enumeration<Driver> driverList= DriverManager.getDrivers();
        while (driverList.hasMoreElements()){
            Driver driverClass=(Driver) driverList.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }
}

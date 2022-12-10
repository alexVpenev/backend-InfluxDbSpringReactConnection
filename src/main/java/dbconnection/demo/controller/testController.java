package dbconnection.demo.controller;

import dbconnection.demo.entity.ITruck;
import dbconnection.demo.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
public class testController {

    @Autowired
    private Environment env ;

    public testController() {

    }


    protected Connection getDatabaseConnection() {

        String url = env.getProperty("spring.datasource.url");
//        String username = env.getProperty();"jdbc:postgresql://52.58.123.96:3636"
//        String password = env.getProperty();


        try {
//            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, "postgres", "asdasd");
            connection.setAutoCommit(false);
            return connection;
        }
        catch (SQLException e) {
//            throw new IllegalStateException("JDBC driver failed to connect to the database " + "jdbc:postgresql://52.58.123.96:3636" + ".", e);
            throw new IllegalStateException(e);
        }

    }


    @GetMapping("/")
    public String testHelloDocker() {
        return "ne moga da povqrvam kolko sum strahoten";
    }

    @GetMapping("/hello")
    public String testHelloWorld() {
        return "General Docker!";
    }

    @GetMapping("/truck")
    public ResponseEntity<Truck> getAllTrucks() {
        Truck truck = new Truck(1, "EK220NMADSF", true, "Alex Penev");
        return ResponseEntity.ok().body(truck);
    }

    @GetMapping("/sql")
    public ResponseEntity<String> testSql() {
        try {
            return ResponseEntity.ok().body(curlSqlRequest());
        }catch (Exception e) {
            return ResponseEntity.ok().body(e.toString());
        }

    }

    private String curlSqlRequest() {

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * from trucks;";
        String serial_number = "1";

        try {



            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();

            String id = resultSet.getString("id");
            serial_number = resultSet.getString("serial_number");
            String model = resultSet.getString("model");
            int dept_id = resultSet.getInt("department_id");



        }

        catch (Exception e) {

            serial_number=e.toString();
        }

        finally {
            try{
                connection.commit();
                connection.close();
            }
            catch (SQLException throwable){
                System.out.println("Can't close connection");
            }
        }
        return serial_number;
    }

//    @GetMapping("/maikaTi")
//    public String maikaTi() {
//
//        ITruck truck = new Truck(1, "EK220NMADSF", true, "Alex Penev");
//        return truck.getMaikaTi();
//    }

    @PostMapping("/webhook")
    public String getMyJson(@RequestBody Map<String, Object> json) {
        return ("" + json);
    }
}

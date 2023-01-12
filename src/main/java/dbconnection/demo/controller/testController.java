package dbconnection.demo.controller;

import dbconnection.demo.entity.ITruck;
import dbconnection.demo.entity.Truck;
import dbconnection.demo.influxdb.InfluxDBConnectionClass;
import dbconnection.demo.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.influxdb.client.InfluxDBClient;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.security.Key;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
public class testController {

    @Autowired
    private Environment env ;

    public testController() {

    }


    @GetMapping("/influx")
    public ResponseEntity<String> testInflux() {

        String token = "Kcq-ml9wvXNMmLp4wugURLkvVdNlt-brxSgFbp3tZB9D-g-ngEctSSHsHYHRrogQ4PuPNmf3PWZdj8_KxpEQVA==";
        String bucket = "BigTrucks";
        String org = "HYG";
        String url = "http://52.58.123.96:8080";

        String riturn = "dqdo ti";

        try{
            InfluxDBConnectionClass inConn = new InfluxDBConnectionClass();
            InfluxDBClient influxDBClient = inConn.buildConnection(url, token, bucket, org);


            riturn = inConn.queryData(influxDBClient);


            influxDBClient.close();
        }
        catch(Exception e){
            return ResponseEntity.ok().body(e.toString());
        }

        return ResponseEntity.ok().body(riturn);





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


//    @PermitAll
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/")
    public String testHelloDocker() {
        return "FINALEN TEST";
    }

//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthority('USER')")
//    @PreAuthorize("hasAuthority('ROLE_Admin')")
//    @RolesAllowed({"USER"})
    @GetMapping("/hello")
    public String testHelloWorld() {
        return "General Docker!";
    }

    @GetMapping("/truck")
    public ResponseEntity<Truck> getAllTrucks() {
        Truck truck = new Truck(1, "EK220NMADSF", true, "Alex Penev");
        return ResponseEntity.ok().body(truck);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<List<Truck>> getTrucks() {
        List<Truck> trucks = new ArrayList<Truck>();
        trucks.add(new Truck(1, "EK220NMADSF", true, "Alex Penev"));
        trucks.add(new Truck(2, "EK123402MDS", false, "Pacos"));
        trucks.add(new Truck(3, "POF235224NA", true, "Bat Boiko"));
        trucks.add(new Truck(4, "ERQ145234fd", true, "Bat Arni"));
        trucks.add(new Truck(5, "KALE3420645", false, "Vasil Levski"));

        return ResponseEntity.ok().body(trucks);
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



//    private UserService userService = new UserService();

    byte[] keyBytes = Decoders.BASE64.decode("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
    Key key = Keys.hmacShaKeyFor(keyBytes);

//    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String response = encode(request.username, request.password);
        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(request.username + "  az sum prost  " + request.password);
    }

    private String encode(String username, String password) {
        Map<String, Object> claimsMap = new HashMap<>();
//        claimsMap.put("role", accessToken.getRole());
//        claimsMap.put("user_id", accessToken.getUser_id());

        claimsMap.put("username", username);
        claimsMap.put("user_id", 2);


//        String[] roles = {"Admin"};
//        List<String> roles = new ArrayList<>();
//        roles.add("Admin");


//        List<String> role = new ArrayList<>();
//
//        List<String> roles = role.stream()
//                .map(userRole-> "USER")
//                .collect(Collectors.toUnmodifiableList());

        claimsMap.put("role", "USER");

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(java.util.Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }



}

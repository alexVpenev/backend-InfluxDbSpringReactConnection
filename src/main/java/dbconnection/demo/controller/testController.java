package dbconnection.demo.controller;

import dbconnection.demo.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
public class testController {

    public testController() {

    }

    @GetMapping("/")
    public String testHelloDocker() {
        return "Jivota na plamen e glupost";
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

    @PostMapping("/webhook")
    public String getMyJson(@RequestBody Map<String, Object> json) {
        return ("" + json);
    }
}

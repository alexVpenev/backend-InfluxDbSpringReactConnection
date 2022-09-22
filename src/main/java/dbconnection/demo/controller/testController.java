package dbconnection.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/test")
public class testController {

    @GetMapping("/hello")
    public String testHelloWorld() {
        return "Hello World!";
    }

    @PostMapping("/webhook")
    public String getMyJson(@RequestBody Map<String, Object> json) {
        return ("" + json);
    }
}

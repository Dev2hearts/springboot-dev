package me.jspark.springdev;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
    @GetMapping("/test")
    public String getTest(){
        return "Hello Everyone!!!";
    }
    @PostMapping("/test")
    public String postTest(
            @RequestBody Map<String, Object> requestBody
    ) {
        System.out.println("전달받은 Body 데이터: " + requestBody);

        return "Post Test response!!!!!!!";
    }

    @DeleteMapping("/test")
    public String deleteTest(){
        return "Delete Test response!!!!!!!";
    }
    @PutMapping("/test")
    public String putTest(){
        return "Put Test response!!!!!!!";
    }
}

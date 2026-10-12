package me.jspark.springdev;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    //요청 URL과 요청 방식(GET, POST, PATCH, PUT, DELETE)에 따라 그 요청을 실행할 메서드를 매핑
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code){
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!"); //201번
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!"); //400번
            default:
                return ResponseEntity.ok().body("OK!"); //200, "OK"
        }
    }
    @PostMapping("/quiz")
    public ResponseEntity<String> postQuiz(@RequestBody Code code){
        System.out.println("=========================["+code+"]");
        switch (code.value()){
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }

    }
}

record Code(int value){}

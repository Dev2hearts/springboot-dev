package me.jspark.springdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    // 요청을 받아서 적절한 비즈니스로직으로 연결
    @Autowired
    MemberService memberService;
    @GetMapping("/member")
    public ResponseEntity<List<Member>> getAllMember(){
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    @PostMapping("/member")
    public ResponseEntity<Member> saveMember(@RequestBody Member member){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(member));
    }
}

package me.jspark.springdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    // 요청을 받아서 적절한 비즈니스로직으로 연결
    @Autowired
    MemberService memberService;
    @GetMapping("/member")
    public List<Member> getAllMember(){
        return memberService.getAllMembers();
    }
}

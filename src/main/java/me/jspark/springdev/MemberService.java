package me.jspark.springdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    //의존성 주입 Dependency Injection
    MemberRepository memberRepository;
    // 멤버 테이블에 있는 모든 레코드들을 읽어서 반환
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
}

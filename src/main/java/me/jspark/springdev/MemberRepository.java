package me.jspark.springdev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//레파지토리 명시
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 메서드
    // findAll() : select * from member;
    // findById() : select * from member where id = ?;
    // deleteAll() : delete from member;
    // deleteById() : delete from member where id = ?;
    // save() : insert into member(id, name) values (?, ?), update member set ... where id = ?;

    // findByName() : select * from member where name = ?;
    public List<Member> findByName(String name);
    // select * from member where email = ?;
//    public Optional<Member> findByEmail(String email);
    // select * from member where name = ? and email = ?;
    // public Optionam<Member> findByNameAndEmail(String name, String email);
}

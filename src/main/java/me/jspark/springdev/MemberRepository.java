package me.jspark.springdev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//레파지토리 명시
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {}

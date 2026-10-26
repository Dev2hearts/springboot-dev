package me.jspark.springdev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insertMembers.sql")
    @DisplayName("member 테이블의 모든 레코드 가져오기")
    @Test
    public void getAllMembers(){
        // given


        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insertMembers.sql")
    @DisplayName("member 테이블에서 id가 2인 레코드 가져오기")
    @Test
    public void getMemberById2() {
        // given


        // when
        Member member = memberRepository.findById(2L).get();


        // then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insertMembers.sql")
    @DisplayName("member 테이블에서 Name가 B인 레코드 가져오기")
    @Test
    public void getMemberByName() {
        // given


        // when
        Member memberName = memberRepository.findByName("B").getFirst();

        // then
        assertThat(memberName.getId()).isEqualTo(2);
    }

    // MemberRepository의 save(), saveAll(), deleteById(), deleteAll() 등의 메서들 작동 테스트
    @DisplayName("1개의 Member 레코드 저장")
    @Test
    public void saveMember() {
        // given
        Member member = new Member("A");

        // when
        memberRepository.save(member);

        // then
        Member selectMember = memberRepository.findByName("A").getFirst();
        assertThat(selectMember.getName()).isEqualTo("A");
    }


    @DisplayName("여러개의 Member 레코드 저장")
    @Test
    public void saveAllMembers(){
        // given
        // for 문
        // List<Member> members = new ArrayList<>();


        List<Member> members = List.of(new Member("A"), new Member("B"), new Member("C"));

        // when
        memberRepository.saveAll(members);

        List<Member> selectAll = memberRepository.findAll();

        // then
        assertThat(selectAll.size()).isEqualTo(members.size());

    }
    @Sql("/insertMembers.sql")
    @DisplayName("id가 2인 레코드 삭제")
    @Test
    public void deleteMemberById(){
        // given

        // when
        memberRepository.deleteById(2L);

        // then
        assertThat(memberRepository.findById(2L)).isNotPresent();
    }
    @Sql("/insertMembers.sql")
    @DisplayName("전체 레코드 삭제")
    @Test
    public void deleteAllMembers(){
        // given

        // when
        memberRepository.deleteAll();

        // then
        assertThat(memberRepository.findAll()).isEmpty();

    }

    // 내부적으로 사용되는 영속성 컨텍스트의 변경감지(dirty-checking) 기능을 이용한 레코드 업데이트
    @Sql("/insertMembers.sql")
    @DisplayName("id가 2인 레코드 업데이트")
    @Test
    public void updateMemberById(){
        // given
        Member member = memberRepository.findById(2L).get();

        // then
        member.changeName("홍길동");
        // Or
        member.setName("홍길동");



        // when
        assertThat(member.getName()).isEqualTo("홍길동");
    }

}
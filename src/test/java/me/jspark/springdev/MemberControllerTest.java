package me.jspark.springdev;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void deleteAll(){
        memberRepository.deleteAll();
    }

    @Test
    void getAllMember() throws Exception{
        // 세개의 단계를 가짐
        // 준비 단계 (Given)
        // 한 명의 회원을 DB테이블에 저장함으로써 등록
        final String url = "/member";
        Member saveMember = memberRepository.save(new Member("박지성"));

        // 실행 단계 (When)
        // 회원 리스트 요청
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // 검증 단계 (Then)
        // 회원 리스트 요청에 의해서 회ㅣ신받은 데이터가 준비 단계에서 저장된 회원 정보과 동일 한지 확인
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(saveMember.getId()))
                .andExpect(jsonPath("$[0].name").value(saveMember.getName()));

    }

    @Test
    void saveMember() throws Exception{
        final String url = "/member";

        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Member("박지성2"))));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()) // ⭐️ 핵심 3: 자동 증가 ID는 고정 숫자 대신 '존재 여부(exists)'만 확인
                .andExpect(jsonPath("$.name").value("박지성2"));

        System.out.println(result.andReturn().getResponse().getContentAsString());

    }
}
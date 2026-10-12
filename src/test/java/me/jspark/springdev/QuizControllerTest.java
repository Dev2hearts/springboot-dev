package me.jspark.springdev;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // 객체를 JSON 문자열로 변환해주는 API

    @DisplayName("GET /quiz?code=1 => 응답 201, 본문은 Created!")
    @Test
    void quiz() throws Exception {
        //given 준비
        final String url = "/quiz";

        // when 실행
        final ResultActions result = mockMvc.perform(get(url).param("code","1"));

        // then 검증
        result.andExpect(status().isCreated())
                .andExpect(content().string("Created!"));

    }

    @Test
    void quiz2() throws Exception{
        final String url = "/quiz";

        final ResultActions result = mockMvc.perform(get(url).param("code","2"));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));
    }

    @Test
    void postQuiz() throws Exception{
        final String url = "/quiz";

        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(1))));

        result.andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden!"));
    }

    @Test
    void postQuiz2() throws Exception{
        final String url = "/quiz";

        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(12))));

        result.andExpect(status().isOk())
                .andExpect(content().string("OK!"));
    }
}
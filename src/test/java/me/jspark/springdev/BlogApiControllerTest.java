package me.jspark.springdev;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.jspark.springdev.dao.Article;
import me.jspark.springdev.dto.ArticleCreationRequest;
import me.jspark.springdev.dto.ArticleUpdateRequest;
import me.jspark.springdev.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogApiControllerTest {

    @AfterEach
    public void clearUp() {
        blogRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BlogRepository blogRepository;

    @DisplayName("Articles 등록 시도")
    @Test
    public void addArticle() throws Exception{
        // 준비 given
        String title = "JUnit Test";
        String content = "JUnit Test Content";
        String url = "/api/articles";
        ArticleCreationRequest req = new ArticleCreationRequest(title,content);
        String articleStr = objectMapper.writeValueAsString(req);

        // 실행 when
        ResultActions result =
                mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleStr));

        // 검증 then
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("목록 조회 테스트")
    @Test
    public void getAllArticles() throws Exception{
        String title = "JUnit Test";
        String content = "JUnit Test Content";
        String url = "/api/articles";
        blogRepository.save(Article.builder().content(content).title(title).build());

        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }
    @DisplayName("아이디로 목록 조회 테스트")
    @Test
    public void getArticlesById() throws Exception{
        String title = "JUnit Test";
        String content = "JUnit Test Content";
        String url = "/api/articles/{id}";
        Article article = blogRepository.save(Article.builder().content(content).title(title).build());

        ResultActions resultActions = mockMvc.perform(
                get(url, article.getId()).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(title));

    }
    @DisplayName("아이디로 게시글 수정")
    @Test
    public void updateArticleById() throws Exception{
        // given
        // article 객체 하나를 생성해서 메서드 체이닝을 통해서 객체 생성
        final String url = "/api/articles/{id}";
        Article savedArticle = blogRepository.save(Article.builder()
                .title("JUnit Test Title")
                .content("JUnit Test Content")
                .build());

        // 변경될 DTO 객체를 생성
        final String newTitle = "JUnit Test Title2";
        final String newContent = "JUnit Test Content2";
        ArticleUpdateRequest request = new ArticleUpdateRequest(newTitle, newContent);

        // when : 실제 수정 API 호출 (서비스의 @Transactional 안에서 더티체킹 동작)
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }
}

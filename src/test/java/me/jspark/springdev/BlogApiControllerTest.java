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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        // 저장할 타이틀
        String title = "JUnit Test";
        // 저장할 컨텐츠
        String content = "JUnit Test Content";
        // 요청할 URL
        String url = "/api/articles";
        // 타이틀과 컨텐츠를 DTO 객체로 생성
        ArticleCreationRequest req = new ArticleCreationRequest(title,content);
        // OBJ맵핑을 사용하여 DTO를 JSON 문자열로 직렬화
        String articleStr = objectMapper.writeValueAsString(req);

        // 실행 when
        // 받을 변수 생성
        ResultActions result =
                // mockMvc 를사용하여 post
                mockMvc.perform(post(url)
                        // 요청 body가 JSON임을 헤더에 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        // 컨텐트를 JSON으로 파싱한 DTO로 삽입
                        .content(articleStr));

        // 검증 then
        // 201번(Created)이 되면
        result.andExpect(status().isCreated());
        // Article 리스트로 인스턴스 생성
        List<Article> articles = blogRepository.findAll();
        // given 준비 단계에서 준비한 변수와 같은지 확인
        assertThat(articles.getFirst().getTitle()).isEqualTo(title);
        assertThat(articles.getFirst().getContent()).isEqualTo(content);
    }

    @DisplayName("목록 조회 테스트")
    @Test
    public void getAllArticles() throws Exception{
        // 준비 given
        // 저장할 타이틀
        String title = "JUnit Test";
        String title2 = "JUnit Test2";
        // 저장할 컨텐츠
        String content = "JUnit Test Content";
        String content2 = "JUnit Test Content2";
        // 요청할 URL
        String url = "/api/articles";
        // 메서드 체이닝과 builder 를통해 Article 생성 후 저장
        blogRepository.save(Article.builder().content(content).title(title).build());
        blogRepository.save(Article.builder().content(content2).title(title2).build());

        // 실행 when
        // 받을 변수 생성
        ResultActions resultActions =
                // mockMvc 를사용하여 get
                mockMvc.perform(get(url)
                        // 응답을 JSON으로 받겠다고 헤더에 설정
                        .accept(MediaType.APPLICATION_JSON));

        // 검증 then
        // 200번(OK)이 되면
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                // jsonPath를 통해 첫번째 타이틀이 given 변수와 같은지 확인
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[1].title").value(title2))
                // jsonPath를 통해 첫번째 컨텐츠가 given 변수와 같은지 확인
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[1].content").value(content2));
    }

    @DisplayName("아이디로 목록 조회 테스트")
    @Test
    public void getArticlesById() throws Exception{
        // 준비 given
        // 저장할 타이틀
        String title = "JUnit Test";
        // 저장할 컨텐츠
        String content = "JUnit Test Content";
        // 요청할 URL ({id} 자리에 경로 변수가 들어감)
        String url = "/api/articles/{id}";
        // builder 를통해 Article 생성 후 저장하고, id 확인을 위해 저장된 엔티티를 변수에 담음
        Article article = blogRepository.save(Article.builder().content(content).title(title).build());

        // 실행 when
        // 받을 변수 생성
        ResultActions resultActions = mockMvc.perform(
                // mockMvc 를사용하여 get, 저장된 article의 id를 {id}에 대입
                get(url, article.getId())
                        // 응답을 JSON으로 받겠다고 헤더에 설정
                        .accept(MediaType.APPLICATION_JSON));

        // 검증 then
        // 200번(OK)이 되면
        resultActions.andExpect(status().isOk())
                // jsonPath를 통해 응답 타이틀이 given 변수와 같은지 확인
                .andExpect(jsonPath("title").value(title));

    }
    @DisplayName("아이디로 게시글 수정")
    @Test
    public void updateArticleById() throws Exception{
        // 준비 given
        // 요청할 URL ({id} 자리에 경로 변수가 들어감)
        final String url = "/api/articles/{id}";
        // article 객체 하나를 메서드 체이닝과 builder 를통해 생성 후 저장하고, id 확인을 위해 저장된 엔티티를 변수에 담음
        Article savedArticle = blogRepository.save(Article.builder()
                .title("JUnit Test Title")
                .content("JUnit Test Content")
                .build());

        // 수정할 타이틀
        final String newTitle = "JUnit Test Title2";
        // 수정할 컨텐츠
        final String newContent = "JUnit Test Content2";
        // 수정할 타이틀과 컨텐츠를 DTO 객체로 생성
        ArticleUpdateRequest request = new ArticleUpdateRequest(newTitle, newContent);

        // 실행 when
        // 실제 수정 API 호출 (서비스의 @Transactional 안에서 더티체킹 동작)
        // 받을 변수 생성
        ResultActions result =
                // mockMvc 를사용하여 put, 저장된 article의 id를 {id}에 대입
                mockMvc.perform(put(url, savedArticle.getId())
                        // 요청 body가 JSON임을 헤더에 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        // OBJ맵핑을 사용하여 DTO를 JSON 문자열로 직렬화한 뒤 body로 삽입
                        .content(objectMapper.writeValueAsString(request)));

        // 검증 then
        // 200번(OK)이 되면
        result.andExpect(status().isOk());

        // 저장된 article의 id로 DB에서 다시 조회 (Optional이므로 get()으로 꺼냄)
        Article article = blogRepository.findById(savedArticle.getId()).get();
        // 수정된 타이틀이 given 단계에서 준비한 newTitle과 같은지 확인
        assertThat(article.getTitle()).isEqualTo(newTitle);
        // 수정된 컨텐츠가 given 단계에서 준비한 newContent와 같은지 확인
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    @DisplayName("Article 삭제")
    @Test
    public void deleteArticle() throws Exception{
        // 준비 given
        // 요청할 URL ({id} 자리에 경로 변수가 들어감)
        final String url = "/api/articles/{id}";
        // article 객체 하나를 메서드 체이닝과 builder 를통해 생성 후 저장하고, id 확인을 위해 저장된 엔티티를 변수에 담음
        Article savedArticle = blogRepository.save(Article.builder()
                .title("JUnit Test Title")
                .content("JUnit Test Content")
                .build());

        // 실행 when
        // mockMvc 를사용하여 delete, 저장된 article의 id를 {id}에 대입
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));
        // 삭제 후 DB에서 전체 목록을 다시 조회
        List<Article> articles = blogRepository.findAll();

        // 결과 then
        // 200번(OK)이 되면
        result.andExpect(status().isOk());
        // 목록이 비어 있는지 확인 (삭제됐는지 확인)
        assertThat((articles)).isEmpty();
    }
}

package me.jspark.springdev.controller;

import lombok.RequiredArgsConstructor;
import me.jspark.springdev.dao.Article;
import me.jspark.springdev.dto.ArticleCreationRequest;
import me.jspark.springdev.dto.ArticleResponse;
import me.jspark.springdev.dto.ArticleUpdateRequest;
import me.jspark.springdev.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// lombok에서 제공하는 생성자 어노테이션(final이 붙은 변수는 초기화를 제공함)
@RequiredArgsConstructor
@RestController
public class BlogApiController {
    // @Autowired 어노테이션 사용 또는
    // 생성자 주입
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest request){
        // 서비스에게 블로그 글 등록 의뢰.
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
        List<Article> articlesList = blogService.findAll();
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        for (Article article : articlesList) {
            articleResponseList.add(new ArticleResponse(article));
        }
        return ResponseEntity.ok(articleResponseList);
    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok(new ArticleResponse(article));
    }
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id){
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticleById(@PathVariable Long id, @RequestBody ArticleUpdateRequest request){
        Article article = blogService.update(id, request);
        ArticleResponse articleResponse = new ArticleResponse(article);
        return ResponseEntity.ok().body(articleResponse);
    }
}

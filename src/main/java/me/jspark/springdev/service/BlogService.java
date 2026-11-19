package me.jspark.springdev.service;

import lombok.RequiredArgsConstructor;
import me.jspark.springdev.dao.Article;
import me.jspark.springdev.dto.ArticleCreationRequest;
import me.jspark.springdev.dto.ArticleUpdateRequest;
import me.jspark.springdev.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest){
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
    public Article findById(Long id){
        return blogRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        blogRepository.deleteById(id);
    }

//    public Article update(Long id, ArticleUpdateRequest req){
//        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Article Empty!"));
//        article.update(req.getTitle(), req.getContent());
//        return blogRepository.save(article);
//    }
//

    @Transactional
    public Article update(Long id , ArticleUpdateRequest req){
        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Article Empty!"));
        article.update(req.getTitle(), req.getContent());
        return article;
    }
}

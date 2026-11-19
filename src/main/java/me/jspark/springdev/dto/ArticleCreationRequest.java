package me.jspark.springdev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jspark.springdev.dao.Article;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ArticleCreationRequest {
    private String title;
    private String content;
    public Article toEntity(){
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }

}

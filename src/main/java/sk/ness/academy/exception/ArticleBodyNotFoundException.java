package sk.ness.academy.exception;

import sk.ness.academy.domain.Article;

public class ArticleBodyNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ArticleBodyNotFoundException(Article article) {
        this.article = article;
    }

    private Article article;

    public Article getArticle() {
        return article;
    }
}
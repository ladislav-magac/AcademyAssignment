package sk.ness.academy.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ArticleBodyNotFoundException;
import sk.ness.academy.exception.ArticleNotFoundException;
import sk.ness.academy.exception.ArticlesNotFoundException;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.CommentRepository;
import sk.ness.academy.repository.NoComment;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  ArticleRepository articleRepository;

  @Autowired
  CommentRepository commentRepository;

  @Override
  public Article findByID(final Integer articleId) {
    Article article = this.articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    article.setComments(this.commentRepository.findByArticleId(articleId));
    return article;
  }

  @Override
  public List<NoComment> findAll() {
    if (this.articleRepository.findAllProjectedBy().isEmpty()) {
      throw new ArticlesNotFoundException();
    }
    return this.articleRepository.findAllProjectedBy();
  }

  @Override
  public void createArticle(final Article article) {
    if     (article.getAuthor() == null   ||
            article.getText()   == null   ||
            article.getTitle()  == null   ||
            article.getAuthor().isBlank() ||
            article.getText()  .isBlank() ||
            article.getTitle() .isBlank()) {
      throw new ArticleBodyNotFoundException(article);
    }
    this.articleRepository.saveAndFlush(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    Arrays.stream(new Gson().fromJson(jsonArticles, Article[].class)).forEach(A -> this.articleRepository.saveAndFlush(A));
  }

  @Override
  public void deleteByID(final Integer articleId) {
    this.articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    this.articleRepository.deleteById(articleId);
  }

  @Override
  public List<Article> findByText(final String searchText) {
    List<Article> list = this.articleRepository.findByAuthorContainingOrTitleContainingOrTextContaining(searchText, searchText, searchText);
    if (list.isEmpty()) {
      throw new ArticlesNotFoundException();
    }
    List<Article> articles = new ArrayList<>();
    for (Article article : list) {
      article.setComments(this.commentRepository.findByArticleId(article.getId()));
      articles.add(article);
    }
    return articles;
  }

}

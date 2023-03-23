package sk.ness.academy.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    //TASK 3
    Arrays.stream(new Gson().fromJson(jsonArticles, Article[].class)).forEach(A -> this.articleDAO.persist(A));
    //throw new UnsupportedOperationException("Article ingesting not implemented.");
    //TASK 3
  }

  //TASK 1
  @Override
  public void deleteArticle(final Article article) {
    this.articleDAO.delete(article);
  }
  //TASK 1

}

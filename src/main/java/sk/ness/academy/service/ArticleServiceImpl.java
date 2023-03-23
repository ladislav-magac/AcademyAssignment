package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
    this.articleDAO.ingestArticles(jsonArticles);
    //throw new UnsupportedOperationException("Article ingesting not implemented.");
    //TASK 3
  }

  //TASK 1
  @Override
  public void deleteByID(final Integer articleId) {
    this.articleDAO.deleteByID(articleId);
  }
  //TASK 1

  //TASK 4
  @Override
  public List<Article> findByText(final String searchText) {
    return this.articleDAO.findByText(searchText);
  }
  //TASK 4

}

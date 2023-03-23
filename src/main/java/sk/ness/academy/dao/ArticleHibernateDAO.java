package sk.ness.academy.dao;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  //TASK 1
  @Override
  public void deleteByID(final Integer articleId) {
    this.sessionFactory.getCurrentSession().delete(this.sessionFactory.getCurrentSession().get(Article.class, articleId));
  }
  //TASK 1

  //TASK 3
  @Override
  public void ingestArticles(String jsonArticles) {
    Arrays.stream(new Gson().fromJson(jsonArticles, Article[].class)).forEach(A -> this.sessionFactory.getCurrentSession().saveOrUpdate(A));
  }
  //TASK 3

  //TASK 4
  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findByText(final String searchText) {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles where author like'%" + searchText + "%' or title like '%" + searchText + "%' or text like '%" + searchText + "%'").addEntity(Article.class).list();
  }
  //TASK 4

}

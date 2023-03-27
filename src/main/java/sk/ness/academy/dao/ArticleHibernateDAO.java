package sk.ness.academy.dao;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
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

  //TASK 2
  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select a.id as id, a.title as title, a.text as text, a.author as author, a.create_timestamp as create_timestamp from articles a")
            .addScalar("id", IntegerType.INSTANCE)
            .addScalar("title", StringType.INSTANCE)
            .addScalar("text", StringType.INSTANCE)
            .addScalar("author", StringType.INSTANCE)
            .addScalar("create_timestamp", DateType.INSTANCE)
            .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  }
  //TASK 2

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

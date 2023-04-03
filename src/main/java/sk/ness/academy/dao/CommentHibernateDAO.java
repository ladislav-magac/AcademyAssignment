//TASK 2
package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Comment> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Comment> findByArticleID(final Integer articleId) {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments where articleId = " + articleId).addEntity(Comment.class).list();
  }

  @Override
  public void deleteById(final Integer commentId) {
    this.sessionFactory.getCurrentSession().delete(this.sessionFactory.getCurrentSession().get(Comment.class, commentId));
  }

  @Override
  public Comment findByID(Integer commentId) {
    return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
  }

}
//TASK 2
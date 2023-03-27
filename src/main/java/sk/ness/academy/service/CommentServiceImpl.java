//TASK 2
package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

  @Resource
  private ArticleDAO articleDAO;

  @Resource
  private CommentDAO commentDAO;

  @Override
  public void createComment(final Integer articleId, final Comment comment) {
    Article article = this.articleDAO.findByID(articleId);
    List<Comment> comments = article.getComments();
    comments.add(comment);
    article.setComments(comments);
    this.articleDAO.persist(article);
  }

  @Override
  public void deleteByID(Integer commentId) {
    this.commentDAO.deleteById(commentId);
  }

  @Override
  public List<Comment> findAll() {
    return this.commentDAO.findAll();
  }

}
//TASK 2
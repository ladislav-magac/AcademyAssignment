package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ArticleNotFoundException;
import sk.ness.academy.exception.CommentBodyNotFoundException;
import sk.ness.academy.exception.CommentNotFoundException;
import sk.ness.academy.exception.CommentsNotFoundException;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

  //BONUS
  @Autowired
  CommentRepository commentRepository;

  @Autowired
  ArticleRepository articleRepository;

  @Override
  public void createComment(final Integer articleId, final Comment comment) {
    //BONUS
    Article article = this.articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    if     (comment.getAuthor() == null   ||
            comment.getText()   == null   ||
            comment.getAuthor().isBlank() ||
            comment.getText()  .isBlank()) {
      throw new CommentBodyNotFoundException(comment);
    }
    List<Comment> comments = article.getComments();
    comments.add(comment);
    article.setComments(comments);
    this.articleRepository.saveAndFlush(article);
  }

  @Override
  public void deleteByID(Integer commentId) {
    this.commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    this.commentRepository.deleteById(commentId);
  }

  @Override
  public List<Comment> findAll() {
    if (this.commentRepository.findAll().isEmpty()) {
      throw new CommentsNotFoundException();
    }
    return this.commentRepository.findAll();
  }

  @Override
  public Comment findByID(Integer commentId) {
    return this.commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
  }

}
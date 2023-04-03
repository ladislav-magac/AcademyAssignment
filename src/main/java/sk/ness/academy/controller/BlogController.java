package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.*;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  //TASK 1
  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
      //TASK 6
      if (this.articleService.findByID(articleId) == null) {
          throw new ArticleNotFoundException();
      }
      //TASK 6
      this.articleService.deleteByID(articleId);
  }
  //TASK 1

  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
      //TASK 6
      if (this.articleService.findAll().isEmpty()) {
        throw new ArticlesNotFoundException();
      }
      //TASK 6
	  return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
      //TASK 6
      if (this.articleService.findByID(articleId) == null) {
        throw new ArticleNotFoundException();
      }
      //TASK 6
	  return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
      //TASK 6
      if (this.articleService.findByText(searchText).isEmpty()) {
        throw new ArticlesNotFoundException();
      }
      //TASK 6
      //TASK 4
      return this.articleService.findByText(searchText);
	  //throw new UnsupportedOperationException("Full text search not implemented.");
      //TASK 4
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody() final Article article) {
      //TASK 6
      if (article.getAuthor() == null   ||
          article.getText()   == null   ||
          article.getTitle()  == null   ||
          article.getAuthor().isBlank() ||
          article.getText()  .isBlank() ||
          article.getTitle() .isBlank()) {
          throw new ArticleBodyNotFoundException(article);
      }
      //TASK 6
      this.articleService.createArticle(article);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
      //TASK 6
      if (this.authorService.findAll().isEmpty()) {
          throw new AuthorsNotFoundException();
      }
      //TASK 6
	  return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
      //TASK 6
      if (this.authorService.findAll().isEmpty()) {
          throw new AuthorsNotFoundException();
      }
      //TASK 6
      //TASK 5
      return this.authorService.authorStats();
	  //throw new UnsupportedOperationException("Author statistics not implemented.");
      //TASK 5
  }

  //TASK 2
  // ~~ Comment
  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer commentId) {
      //TASK 6
      if (this.commentService.findByID(commentId) == null) {
          throw new CommentNotFoundException();
      }
      //TASK 6
      this.commentService.deleteByID(commentId);
  }
  //TASK 2

  @RequestMapping(value = "comments", method = RequestMethod.GET)
  public List<Comment> getAllComments() {
      //TASK 6
      if (this.commentService.findAll().isEmpty()) {
          throw new CommentsNotFoundException();
      }
      //TASK 6
      return this.commentService.findAll();
  }

  @RequestMapping(value = "comments/{articleId}", method = RequestMethod.PUT)
  public void addComment(@PathVariable final Integer articleId, @RequestBody final Comment comment) {
      //TASK 6
      if (this.articleService.findByID(articleId) == null) {
          throw new ArticleNotFoundException();
      }
      if     (comment.getAuthor() == null   ||
              comment.getText()   == null   ||
              comment.getAuthor().isBlank() ||
              comment.getText()  .isBlank()) {
          throw new CommentBodyNotFoundException(comment);
      }
      //TASK 6
      this.commentService.createComment(articleId, comment);
  }

}

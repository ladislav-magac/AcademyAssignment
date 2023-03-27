//TASK 2
package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

	  /** Returns all available {@link Article}s */
	  List<Comment> findAll();

	  /** Deletes {@link Comment} with provided ID */
	  void deleteById(Integer commentId);
}
//TASK 2
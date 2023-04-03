//TASK 2
package sk.ness.academy.dao;

import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

	  /** Deletes {@link Comment} with provided ID */
	  void deleteById(Integer commentId);

	  /** Returns {@link Comment} with provided ID */
	  Comment findByID(Integer commentId);

	  /** Returns all available {@link Comment}s */
	  List<Comment> findAll();

	  /** Returns all available {@link Comment}s with provided Article ID */
	  List<Comment> findByArticleID(Integer articleId);
}
//TASK 2
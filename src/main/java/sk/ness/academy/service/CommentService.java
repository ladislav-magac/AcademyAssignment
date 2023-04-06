package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentService {

	  /** Creates new {@link Comment} */
	  void createComment(Integer articleId, Comment comment);

	  /** Deletes {@link Comment} with provided ID */
	  void deleteByID(Integer commentId);

	  /** Returns all available {@link Comment}s */
	  List<Comment> findAll();

	  /** Returns {@link Comment} with provided ID */
	  Comment findByID(Integer commentId);
}
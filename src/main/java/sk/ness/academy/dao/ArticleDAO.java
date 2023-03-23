package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

	  //TASK 1
	  /** Deletes {@link Article} */
	  void delete(Article article);
	  //TASK 1

	  //TASK 4
	  /** Returns all {@link Article}s where author, title or text contains the searched text */
	  List<Article> findByText(String searchText);
	  //TASK 4
}

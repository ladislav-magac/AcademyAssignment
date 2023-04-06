package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.repository.NoComment;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<NoComment> findAll();

	  /** Creates new {@link Article} */
	  void createArticle(Article article);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	  /** Deletes {@link Article} with provided ID */
	  void deleteByID(Integer articleId);

	  /** Returns all {@link Article}s where author, title or text contains the searched text */
	  List<Article> findByText(String searchText);
}

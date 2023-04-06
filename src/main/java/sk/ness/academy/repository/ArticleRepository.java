package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<NoComment> findAllProjectedBy();

    List<Article> findByAuthorContainingOrTitleContainingOrTextContaining(String searchText1, String searchText2, String searchText3);

    @Query("select distinct new sk.ness.academy.dto.Author(a.author) from Article a")
    List<Author> findAuthors();

    @Query("select new sk.ness.academy.dto.AuthorStats(a.author, count(a.author)) from Article a group by a.author")
    List<AuthorStats> authorStats();
}
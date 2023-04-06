package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "select * from comments where article_id = ?1", nativeQuery = true)
    List<Comment> findByArticleId(Integer articleId);
}
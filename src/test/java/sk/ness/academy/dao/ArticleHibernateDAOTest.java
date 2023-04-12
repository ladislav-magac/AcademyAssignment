package sk.ness.academy.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
class ArticleHibernateDAOTest {

    @Autowired
    private ArticleDAO articleDAO;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(3);
        article.setTitle("Title 3");
        article.setText("Text 3");
        article.setAuthor("Author 3");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByID() {
        final Article article1 = articleDAO.findByID(1);
        final Article article2 = articleDAO.findByID(2);
        final Article article3 = articleDAO.findByID(3);

        final List<Comment> comment1 = article1.getComments();
        final List<Comment> comment2 = article2.getComments();

        //HAPPY PATH
        Assertions.assertEquals("Title 1", article1.getTitle());
        Assertions.assertEquals("Title 2", article2.getTitle());
        Assertions.assertEquals("Text 11", comment1.get(0).getText());
        Assertions.assertEquals("Text 12", comment1.get(1).getText());
        Assertions.assertEquals("Text 21", comment2.get(0).getText());
        Assertions.assertEquals("Text 22", comment2.get(1).getText());

        //UNHAPPY PATH
        Assertions.assertNull(article3);
    }

    @Test
    void findAll() {
        final List<Article> articleList = articleDAO.findAll();

        Assertions.assertEquals(2, articleList.size());
    }

    @Test
    void persist() {
        articleDAO.persist(article);

        final Article article = articleDAO.findByID(3);

        Assertions.assertEquals("Title 3", article.getTitle());
    }

    @Test
    void deleteByID() {
        articleDAO.deleteByID(2);

        final Article article = articleDAO.findByID(2);

        Assertions.assertNull(article);
    }

    @Test
    void ingestArticles() throws IOException {
        final Path path = Path.of("articles_to_ingest.txt");

        articleDAO.ingestArticles(Files.readString(path));

        final List<Article> article = articleDAO.findByText("ing");

        Assertions.assertEquals("Matt Lacey", article.get(0).getAuthor());
        Assertions.assertEquals("Ruth Avramovich", article.get(1).getAuthor());
        Assertions.assertEquals("Emil Forslund", article.get(2).getAuthor());
    }

    @Test
    void findByText() {
        final List<Article> article = articleDAO.findByText("Text");

        Assertions.assertEquals(2, article.size());
        Assertions.assertEquals("Text 1", article.get(0).getText());
        Assertions.assertEquals("Text 2", article.get(1).getText());
    }
}
package sk.ness.academy.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class })
class ArticleServiceImplTest {

    @Mock
    private ArticleDAO articleDAO;

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article article1;

    private Article article2;

    private Article article3;

    private List<Article> articles;

    private List<Comment> comments1;

    private List<Comment> comments2;

    @BeforeEach
    void setUp() {
        article1 = new Article();
        article1.setId(1);
        article1.setTitle("Title 1");
        article1.setText("Text 1");
        article1.setAuthor("Author 1");

        final Comment comment11 = new Comment();
        comment11.setId(11);
        comment11.setAuthor("Author 11");
        comment11.setText("Text 11");

        final Comment comment12 = new Comment();
        comment12.setId(12);
        comment12.setAuthor("Author 12");
        comment12.setText("Text 12");

        comments1 = new ArrayList<>();
        comments1.add(comment11);
        comments1.add(comment12);

        article1.setComments(comments1);

        article2 = new Article();
        article2.setId(2);
        article2.setTitle("Title 2");
        article2.setText("Text 2");
        article2.setAuthor("Author 2");

        final Comment comment21 = new Comment();
        comment21.setId(21);
        comment21.setAuthor("Author 21");
        comment21.setText("Text 21");

        final Comment comment22 = new Comment();
        comment22.setId(22);
        comment22.setAuthor("Author 22");
        comment22.setText("Text 22");

        comments2 = new ArrayList<>();
        comments2.add(comment21);
        comments2.add(comment22);

        article2.setComments(comments2);

        article3 = new Article();
        article3.setId(3);
        article3.setTitle("Title 3");
        article3.setText("Text 3");
        article3.setAuthor("Author 3");

        final Comment comment31 = new Comment();
        comment31.setId(31);
        comment31.setAuthor("Author 31");
        comment31.setText("Text 31");

        final Comment comment32 = new Comment();
        comment32.setId(32);
        comment32.setAuthor("Author 32");
        comment32.setText("Text 32");

        final List<Comment>comments3 = new ArrayList<>();
        comments3.add(comment31);
        comments3.add(comment32);

        article3.setComments(comments3);

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByID() {
        Mockito.when(articleDAO.findByID(1)).thenReturn(article1);
        Mockito.when(articleDAO.findByID(2)).thenReturn(article2);
        Mockito.when(commentDAO.findByArticleID(1)).thenReturn(comments1);
        Mockito.when(commentDAO.findByArticleID(2)).thenReturn(comments2);

        final Article article1 = articleService.findByID(1);
        final Article article2 = articleService.findByID(2);
        final Article article3 = articleService.findByID(3);

        //HAPPY PATH
        Assertions.assertEquals("Title 1", article1.getTitle());
        Assertions.assertEquals("Title 2", article2.getTitle());

        //UNHAPPY PATH
        Assertions.assertNull(article3);
    }

    @Test
    void findAll() {
        Mockito.when(articleDAO.findAll()).thenReturn(articles);

        final List<Article> articleList = articleService.findAll();

        Assertions.assertEquals(2, articleList.size());
        Assertions.assertEquals("Author 1", articleList.get(0).getAuthor());
        Assertions.assertEquals("Author 2", articleList.get(1).getAuthor());
    }

    @Test
    void createArticle() {
        articleService.createArticle(article3);
        Mockito.verify(articleDAO, Mockito.times(1)).persist(article3);
    }

    @Test
    void ingestArticles() throws IOException {
        final Path path = Path.of("articles_to_ingest.txt");
        articleService.ingestArticles(Files.readString(path));
        Mockito.verify(articleDAO, Mockito.times(1)).ingestArticles(Files.readString(path));

    }

    @Test
    void deleteByID() {
        articleService.deleteByID(3);
        Mockito.verify(articleDAO, Mockito.times(1)).deleteByID(3);
    }

    @Test
    void findByText() {
        Mockito.when(articleDAO.findByText("Text")).thenReturn(articles);
        Mockito.when(articleDAO.findByText("Wrong Text")).thenReturn(Collections.emptyList());

        final List<Article> articleList = articleService.findByText("Text");
        final List<Article> emptyList = articleService.findByText("Wrong Text");

        Assertions.assertEquals(2, articleList.size());
        Assertions.assertEquals("Text 1", articleList.get(0).getText());
        Assertions.assertEquals("Text 2", articleList.get(1).getText());

        Assertions.assertEquals(0, emptyList.size());
    }
}
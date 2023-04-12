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
import sk.ness.academy.dao.CommentHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, CommentHibernateDAO.class })
class CommentServiceImplTest {

    @Mock
    private CommentDAO commentDAO;

    @Mock
    private ArticleDAO articleDAO;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Article article;

    private Comment comment11;

    private Comment comment12;

    private Comment comment13;

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(1);
        article.setTitle("Title 1");
        article.setText("Text 1");
        article.setAuthor("Author 1");

        comment11 = new Comment();
        comment11.setId(11);
        comment11.setAuthor("Author 11");
        comment11.setText("Text 11");

        comment12 = new Comment();
        comment12.setId(12);
        comment12.setAuthor("Author 12");
        comment12.setText("Text 12");

        comment13 = new Comment();
        comment13.setId(13);
        comment13.setAuthor("Author 13");
        comment13.setText("Text 13");

        comments = new ArrayList<>();
        comments.add(comment11);
        comments.add(comment12);

        article.setComments(comments);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createComment() {
        Mockito.when(articleDAO.findByID(1)).thenReturn(article);
        commentService.createComment(1, comment13);
        Mockito.verify(articleDAO, Mockito.times(1)).persist(article);
    }

    @Test
    void deleteByID() {
        commentService.deleteByID(3);
        Mockito.verify(commentDAO, Mockito.times(1)).deleteById(3);
    }

    @Test
    void findAll() {
        Mockito.when(commentDAO.findAll()).thenReturn(comments);

        final List<Comment> commentList = commentService.findAll();

        Assertions.assertEquals(2, commentList.size());
        Assertions.assertEquals("Text 11", commentList.get(0).getText());
        Assertions.assertEquals("Text 12", commentList.get(1).getText());
    }

    @Test
    void findByID() {
        Mockito.when(commentDAO.findByID(11)).thenReturn(comment11);
        Mockito.when(commentDAO.findByID(12)).thenReturn(comment12);

        final Comment comment11 = commentService.findByID(11);
        final Comment comment12 = commentService.findByID(12);
        final Comment comment13 = commentService.findByID(13);

        //HAPPY PATH
        Assertions.assertEquals("Author 11", comment11.getAuthor());
        Assertions.assertEquals("Author 12", comment12.getAuthor());

        //UNHAPPY PATH
        Assertions.assertNull(comment13);
    }
}
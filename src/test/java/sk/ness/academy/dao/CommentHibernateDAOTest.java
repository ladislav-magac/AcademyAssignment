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
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, CommentHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
class CommentHibernateDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        final List<Comment> commentList = commentDAO.findAll();

        Assertions.assertEquals(4, commentList.size());
        Assertions.assertEquals("Author 11", commentList.get(0).getAuthor());
        Assertions.assertEquals("Author 12", commentList.get(1).getAuthor());
        Assertions.assertEquals("Author 21", commentList.get(2).getAuthor());
        Assertions.assertEquals("Author 22", commentList.get(3).getAuthor());
    }

    @Test
    void findByArticleID() {
        final List<Comment> commentList1 = commentDAO.findByArticleID(1);
        final List<Comment> commentList2 = commentDAO.findByArticleID(2);
        final List<Comment> commentList3 = commentDAO.findByArticleID(3);

        final Comment comment1 = commentList1.get(0);
        final Comment comment2 = commentList1.get(1);
        final Comment comment3 = commentList2.get(0);
        final Comment comment4 = commentList2.get(1);

        //HAPPY PATH
        Assertions.assertEquals(2, commentList1.size());
        Assertions.assertEquals(2, commentList2.size());
        Assertions.assertEquals("Text 11", comment1.getText());
        Assertions.assertEquals("Text 12", comment2.getText());
        Assertions.assertEquals("Text 21", comment3.getText());
        Assertions.assertEquals("Text 22", comment4.getText());

        //UNHAPPY PATH
        Assertions.assertEquals(0, commentList3.size());
    }

    @Test
    void deleteById() {
        commentDAO.deleteById(4);

        final Comment comment = commentDAO.findByID(4);

        Assertions.assertNull(comment);
    }

    @Test
    void findByID() {
        final List<Comment> commentList = commentDAO.findAll();

        final Comment comment1 = commentDAO.findByID(3);
        final Comment comment2 = commentDAO.findByID(4);
        final Comment comment3 = commentDAO.findByID(5);
        final Comment comment4 = commentDAO.findByID(6);
        final Comment comment5 = commentDAO.findByID(7);

        //HAPPY PATH
        Assertions.assertEquals("Text 11", comment1.getText());
        Assertions.assertEquals("Text 12", comment2.getText());
        Assertions.assertEquals("Text 21", comment3.getText());
        Assertions.assertEquals("Text 22", comment4.getText());

        //UNHAPPY PATH
        Assertions.assertNull(comment5);
    }
}
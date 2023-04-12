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
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, AuthorHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
class AuthorHibernateDAOTest {

    @Autowired
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        final List<Author> authorList = authorDAO.findAll();

        Assertions.assertEquals(2, authorList.size());
        Assertions.assertEquals("Author 1", authorList.get(0).getName());
        Assertions.assertEquals("Author 2", authorList.get(1).getName());
    }

    @Test
    void authorStats() {
        final List<AuthorStats> authorStatsList = authorDAO.authorStats();

        Assertions.assertEquals(2, authorStatsList.size());
        Assertions.assertEquals("Author 1", authorStatsList.get(0).getAuthorName());
        Assertions.assertEquals("Author 2", authorStatsList.get(1).getAuthorName());
        Assertions.assertEquals(1, authorStatsList.get(0).getArticleCount());
        Assertions.assertEquals(1, authorStatsList.get(1).getArticleCount());
    }
}
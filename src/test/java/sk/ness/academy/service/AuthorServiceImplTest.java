package sk.ness.academy.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AuthorServiceImplTest {

    @Mock
    private AuthorDAO authorDAO;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors;

    private List<AuthorStats> authorStats;

    @BeforeEach
    void setUp() {
        Author author1 = new Author();
        author1.setName("Name 1");

        Author author2 = new Author();
        author2.setName("Name 2");

        authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        AuthorStats authorStats1 = new AuthorStats();
        authorStats1.setAuthorName("Name 1");
        authorStats1.setArticleCount(1);

        AuthorStats authorStats2 = new AuthorStats();
        authorStats2.setAuthorName("Name 2");
        authorStats2.setArticleCount(2);

        authorStats = new ArrayList<>();
        authorStats.add(authorStats1);
        authorStats.add(authorStats2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        Mockito.when(authorDAO.findAll()).thenReturn(authors);

        final List<Author> authorList = authorService.findAll();

        Assertions.assertEquals(2, authorList.size());
        Assertions.assertEquals("Name 1", authorList.get(0).getName());
        Assertions.assertEquals("Name 2", authorList.get(1).getName());
    }

    @Test
    void authorStats() {
        Mockito.when(authorDAO.authorStats()).thenReturn(authorStats);

        final List<AuthorStats> authorStatsList = authorService.authorStats();

        Assertions.assertEquals(2, authorStatsList.size());
        Assertions.assertEquals("Name 1", authorStatsList.get(0).getAuthorName());
        Assertions.assertEquals("Name 2", authorStatsList.get(1).getAuthorName());
        Assertions.assertEquals(1, authorStatsList.get(0).getArticleCount());
        Assertions.assertEquals(2, authorStatsList.get(1).getArticleCount());
    }
}
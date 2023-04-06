package sk.ness.academy.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.AuthorsNotFoundException;
import sk.ness.academy.repository.ArticleRepository;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

  @Autowired
  ArticleRepository articleRepository;

  @Override
  public List<Author> findAll() {
    if (this.articleRepository.findAuthors().isEmpty()) {
      throw new AuthorsNotFoundException();
    }
    return this.articleRepository.findAuthors();
  }

  @Override
  public List<AuthorStats> authorStats() {
    //BONUS
    if (this.articleRepository.findAuthors().isEmpty()) {
      throw new AuthorsNotFoundException();
    }
    return this.articleRepository.authorStats();
  }

}

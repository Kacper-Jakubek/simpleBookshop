package pl.sdacademy.bookstore.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.AuthorEntity;
import pl.sdacademy.bookstore.model.Author;
import pl.sdacademy.bookstore.repository.AuthorRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @BeforeEach
    void clearBeforeEach(){
        authorRepository.deleteAll();
    }

    @Test
    void shouldAddNewAuthor() {
        Author author = new Author();
        author.setFirstName("A.J.");
        author.setLastName("Kaziński");

        Author saved = authorService.addAuthor(author);
        long savedId = saved.getId();

        AuthorEntity found = authorRepository.getById(savedId).orElse(new AuthorEntity());

        assertThat(saved.getId()).isEqualTo(found.getId());
        assertThat(saved.getFirstName()).isEqualTo(found.getFirstName());
        assertThat(saved.getLastName()).isEqualTo(found.getLastName());
    }

    @Test
        void shouldChangeExistingAuthor() {
        Author author = new Author();
        author.setFirstName("A.J.");
        author.setLastName("Kaziński");

     Author saved = authorService.addAuthor(author);

     saved.setFirstName("Eric Emmanuel");
     saved.setLastName("Schmitt");
     Author updated = authorService.changeAuthor(saved);

     assertThat(updated).isNotNull();
     assertThat(updated.getId()).isEqualTo(saved.getId());
     assertThat(updated.getFirstName()).isEqualTo(saved.getFirstName());
     assertThat(updated.getLastName()).isEqualTo(saved.getLastName());

    }

    @Test
    void shouldDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("A.J.");
        author.setLastName("Kaziński");

        Author saved = authorService.addAuthor(author);
        long foundId = saved.getId();

        authorService.deleteAuthor(saved);

        Optional<Author> found = authorService.findById(foundId);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() ->found.orElseThrow(NoSuchElementException::new));

    }
}

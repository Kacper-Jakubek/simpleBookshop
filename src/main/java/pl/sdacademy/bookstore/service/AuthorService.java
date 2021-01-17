package pl.sdacademy.bookstore.service;


import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.db.AuthorEntity;
import pl.sdacademy.bookstore.model.Author;
import pl.sdacademy.bookstore.mapper.AuthorMapper;
import pl.sdacademy.bookstore.repository.AuthorRepository;

import java.util.Optional;


@Service
public class AuthorService {
    AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author){
        //TODO utwórz walidację dodania autora
        AuthorEntity authorEntity = authorMapper.map(author);
        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return authorMapper.map(savedAuthor);
    }
    public Author changeAuthor(Author author){
        //TODO utwórz walidację edycji autora
        AuthorEntity authorEntity = authorMapper.map(author);
        AuthorEntity savedAuthor = authorRepository.update(authorEntity);
        return authorMapper.map(savedAuthor);
    }
    public void deleteAuthor(Author author){
        //TODO utwórz walidację usunięcia autora
        AuthorEntity authorEntity = authorMapper.map(author);
        authorRepository.delete(authorEntity);
    }

    public Optional<Author> findById(long id) {
        Optional<AuthorEntity> found = authorRepository.getById(id);
        return found.map(authorEntity -> authorMapper.map(authorEntity));
    }

}

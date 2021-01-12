package pl.sdacademy.bookstore.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void findBookByCategoryAndAuthor(){
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName("Andrzej");
        authorEntity.setLastName("Sapkowski");
        entityManager.persist(authorEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Fantasy");
        categoryEntity.setLeaf(false);
        entityManager.persist(categoryEntity);

        Set<AuthorEntity> authors = new HashSet<>();
        authors.add(authorEntity);

        Set<CategoryEntity> categories = new HashSet<>();
        categories.add(categoryEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("Wiedźmin");
        productEntity.setDescription("świetna książka o przygodach wiedźmina");
        BigInteger price = new BigInteger("40"); // czy to tak trzeba?
        productEntity.setPrice(price);
        productEntity.setAvailable(true);
        productEntity.setMiniature("https://allegro.pl/oferta/saga-wiedzmin-pakiet-8-tomow-sapkowski-opr-gra-8788126431");
        productEntity.setAuthors(authors);
        productEntity.setCategories(categories);
        entityManager.persist(productEntity);

        ProductEntity result = entityManager.find(ProductEntity.class, 1L);

            assertNotNull(productEntity);
            assertThat(productEntity.getCategories()).isNotNull();
            assertThat(productEntity.getCategories()).isEqualTo(result.getCategories());
            assertThat(productEntity.getAuthors()).isNotNull();
            assertThat(productEntity.getAuthors()).isEqualTo(result.getAuthors());
            assertThat(productEntity.getDescription()).isEqualTo(result.getDescription());
            assertThat(productEntity.getMiniature()).isEqualTo(result.getMiniature());
            assertThat(productEntity.getPrice()).isEqualTo(result.getPrice());
            assertThat(productEntity.getTitle()).isEqualTo(result.getTitle());
    }

}

package pl.sdacademy.bookstore.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

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

        List<AuthorEntity> authors = new ArrayList<>();
        authors.add(authorEntity);

        Set<CategoryEntity> categories = new HashSet<>();
        categories.add(categoryEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("Wiedźmin");
        productEntity.setDescription("świetna książka o przygodach wiedźmina");
        BigDecimal price = new BigDecimal(40.99); // czy to tak trzeba?
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

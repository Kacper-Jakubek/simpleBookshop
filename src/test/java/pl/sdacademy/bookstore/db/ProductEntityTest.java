package pl.sdacademy.bookstore.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import javax.persistence.EntityManager;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
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
        productEntity.setMiniature("https://www.google.com/search?q=roadie+umbillical+brothers&tbm=isch&ved=2ahUKEwj7ts2Tg5HuAhVIlYsKHXK0CQYQ2-cCegQIABAA&oq=roadie+umbillical+brothers&gs_lcp=CgNpbWcQAzoCCAA6BAgAEB5QvQxYkjVgjDZoAXAAeACAAW6IAckMkgEEMjAuMZgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=IcX6X7uiAsiqrgTy6KYw&bih=938&biw=1920&client=firefox-b-d#imgrc=u8hVZWX2xqpM8M");
        productEntity.setAuthors(authors);
        productEntity.setCategories(categories);

        if(productEntity.getAuthors() == authorEntity && productEntity.getCategories() == categoryEntity){
            assertNotNull(productEntity);
            assertThat(productEntity.getCategories()).isEqualTo(categoryEntity);
            assertThat(productEntity.getAuthors()).isEqualTo(authorEntity);

        }

    }

}

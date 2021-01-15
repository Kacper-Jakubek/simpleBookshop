package pl.sdacademy.bookstore.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.AddressEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    @BeforeEach
    void clearTable() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveNewAddress() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Katowice");
        addressEntity.setCountry("Poland");
        addressEntity.setStreet("Chorzowska");
        addressEntity.setZipCode("41-900");

        AddressEntity result = repository.save(addressEntity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
    }
}

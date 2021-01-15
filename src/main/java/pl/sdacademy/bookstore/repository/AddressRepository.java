package pl.sdacademy.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sdacademy.bookstore.db.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
}

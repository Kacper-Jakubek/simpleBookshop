package pl.sdacademy.bookstore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.sdacademy.bookstore.db.AuthorEntity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


    @Repository
    public class AuthorRepository {
        private final EntityManager entityManager;

        @Autowired
        public AuthorRepository(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        @Transactional
        public AuthorEntity save(AuthorEntity authorEntity){
            entityManager.persist(authorEntity);
            return authorEntity;
        }

        @Transactional
        public AuthorEntity update(AuthorEntity authorEntity){
            entityManager.merge(authorEntity);
            return authorEntity;
        }

        @Transactional
        public Optional<AuthorEntity> getById(long id){
            return Optional.ofNullable(entityManager.find(AuthorEntity.class, id));
        }

        @Transactional
        public List<AuthorEntity> findAll(){
            return entityManager.createQuery
                    ("select a from author a", AuthorEntity.class).getResultList();
        }

        @Transactional
        public void delete(AuthorEntity authorEntity){
            entityManager.remove
                    (entityManager.contains(authorEntity)?authorEntity:entityManager.merge(authorEntity));
        }

        @Transactional
        public void deleteById(long id){
            entityManager.createQuery("delete from author a where a.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Transactional
        public void deleteAll(){
            entityManager.createQuery("delete from author a")
                    .executeUpdate();
        }

        @Transactional
        public void truncate(){
            entityManager.createNativeQuery("truncate table author")
                    .executeUpdate();
        }

}

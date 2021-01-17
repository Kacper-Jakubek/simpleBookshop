package pl.sdacademy.bookstore.db;

import javax.persistence.*;

@Entity(name = "Token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @OneToOne
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UserEntity getAppUser() {
        return userEntity;
    }

    public void setAppUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

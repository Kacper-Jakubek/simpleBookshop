package pl.sdacademy.bookstore.db;

import javax.persistence.*;

// FIXME: when table user is ready
//@Entity
//@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // FIXME: uncomment when Person table is ready
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "person_id", referencedColumnName = "id")
//    private PersonEntity person;
    private boolean isAdmin;
    private boolean isActive;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

package pl.sdacademy.bookstore.db;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name ="author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String AuthorsFname;
    private String AuthorLname;

    public AuthorEntity() {
    }

    public AuthorEntity(Integer Id,String AuthorsFname, String AuthorsLname) {
        this.Id = Id;
        this.AuthorLname = AuthorsLname;
        this.AuthorsFname = AuthorsFname;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public AuthorEntity(Integer id) {
        Id = id;
    }

    public String getAuthorsFname() {
        return AuthorsFname;
    }

    public void setAuthorsFname(String authorsFname) {
        AuthorsFname = authorsFname;
    }

    public String getAuthorLname() {
        return AuthorLname;
    }

    public void setAuthorLname(String authorLname) {
        AuthorLname = authorLname;
    }

    public AuthorEntity(String authorsFname, String authorLname) {
        AuthorsFname = authorsFname;
        AuthorLname = authorLname;
    }
}

package pl.sdacademy.bookstore.db;

public class ProductEntity {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductEntity(String name) {
        this.name = name;
    }
}

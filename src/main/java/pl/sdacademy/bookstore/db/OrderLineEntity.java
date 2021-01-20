package pl.sdacademy.bookstore.db;

import javax.persistence.*;

//@Entity(name = "order")
public class OrderLineEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double price;
    private String bookName;
    //@ManyToOne
    private OrderLineEntity createOrderLine;


    public OrderLineEntity(int id, int quantity, double price, OrderLineEntity createOrderLine, String bookName) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.createOrderLine = createOrderLine;
        this.bookName = bookName;

    }

    public OrderLineEntity() {
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getBookName() { return bookName; }

    public void setBookName(String bookName) { this.bookName = bookName; }

    public OrderLineEntity getCreateOrderLine() { return createOrderLine; }

    public void setCreateOrderLine(OrderLineEntity createOrderLine) { this.createOrderLine = createOrderLine; }

    public boolean getHowMany() { return false; }

    public boolean getBookPrice() { return false; }
}

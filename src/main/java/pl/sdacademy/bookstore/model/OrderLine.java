package pl.sdacademy.bookstore.model;

import pl.sdacademy.bookstore.DTO.ProductDTO;

public class OrderLine {
    private final int id;
    private final ProductDTO productDTO;
    private int quantity;
    private final double price;
    private double netWorth;

    public int getId() {
        return id;
    }

    public ProductDTO getProduct() {
        return productDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public OrderLine(int id, ProductDTO productDTO, int quantity, double price, double netWorth) {
        this.id = id;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.price = price;
        this.netWorth = netWorth;
    }
}

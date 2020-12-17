package pl.sdacademy.bookstore.model;

import java.util.ArrayList;

public class Cart {
private ArrayList<OrderLine> ListofProducts = new ArrayList<>();

    public ArrayList<OrderLine> getListofProducts() {
        return ListofProducts;
    }

    public ArrayList<OrderLine> addProductToCart(OrderLine orderLine){
     ListofProducts.add(orderLine);
     return ListofProducts;
 }

}

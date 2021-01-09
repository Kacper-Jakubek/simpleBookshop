package pl.sdacademy.bookstore.model;

import java.util.ArrayList;

public class Cart {
private ArrayList<OrderLine> ListofProducts = new ArrayList<>();

    public ArrayList<OrderLine> getListofProducts() {
        return ListofProducts;
    }

    public ArrayList<OrderLine> addProduct(OrderLine orderLine){
     ListofProducts.add(orderLine);
     return ListofProducts;
 }
 public ArrayList<OrderLine> removeProduct(int id){
        ListofProducts.remove(id);
        return ListofProducts;
 }
 public boolean checkIfNextProductExists(int id){
        return ListofProducts.size()<id+1;
 }

}

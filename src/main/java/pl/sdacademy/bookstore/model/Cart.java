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
 public boolean removeProduct(OrderLine orderLine){
        int size = ListofProducts.size();
        int index = ListofProducts.indexOf(orderLine);
     ListofProducts.remove(index);
        return ListofProducts.size()==size-1;
 }
 public boolean checkIfNextProductExists(OrderLine orderLine){
     int index = ListofProducts.indexOf(orderLine);
     return ListofProducts.size()>index+1;
 }

}

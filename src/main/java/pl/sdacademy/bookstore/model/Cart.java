package pl.sdacademy.bookstore.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Cart {
private ArrayList<OrderLine> ListofProducts = new ArrayList<>();
private LocalTime creationTime;

    public Cart() {
    }

    public Cart(LocalTime creationTime) {
        this.creationTime = creationTime;
    }

    public ArrayList<OrderLine> getListofProducts() {
        return ListofProducts;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public ArrayList<OrderLine> addProduct(OrderLine orderLine){
     ListofProducts.add(orderLine);
     return ListofProducts;
 }
 public boolean updateProduct(OrderLine orderLine){
     if(ListofProducts.contains(orderLine)){
        int index = ListofProducts.indexOf(orderLine);
        ListofProducts.set(index,orderLine);
        return true;
     }
     return false;
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
public LocalTime getDeleteTime(){
       return getCreationTime().plusHours(48L);
}
}

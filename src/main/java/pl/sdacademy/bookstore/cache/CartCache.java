package pl.sdacademy.bookstore.cache;

import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Component
public class CartCache {
    Map<String, Cart> cartsInMemory = new HashMap();
    private String sessionID;



    public boolean storeCart(Cart cart, String sessionID){
        cartsInMemory.put(sessionID,cart);
        return cartsInMemory.get(sessionID).equals(cart);
    }
    public void addNewProductToCart(String sessionID, OrderLine product){
        Cart cart = cartsInMemory.get(sessionID);
        cart.addProduct(product);
        cartsInMemory.replace(sessionID,cart);
    }
    public void removeProductFromCart(String sessionID, OrderLine product){
        Cart cart = cartsInMemory.get(sessionID);
        cart.removeProduct(product);
        if(cart.checkIfNextProductExists(product)){
            int idCount=0;
            for (OrderLine o:cart.getListofProducts()) {
                o.setId(idCount++);
            }
        }
            cartsInMemory.replace(sessionID,cart);
    }
    public void updateProductInCart(String sessionID, OrderLine product){
        Cart cart = cartsInMemory.get(sessionID);
        cart.updateProduct(product);
    }
    public boolean checkIfCartExist(String sessionID){
        return cartsInMemory.get(sessionID)!=null;
    }

    public int getNextId(){
        return cartsInMemory.size();
    }

    public OrderLine getOrderLine(String sessionID, ProductDTO productDTO){
        Cart cart = cartsInMemory.get(sessionID);
        ArrayList<OrderLine> orderLines = cart.getListofProducts();
        for (OrderLine o:orderLines) {
            if(o.getProduct().equals(productDTO)){
                return o;
            }
        }
    return null;
    }
    public Cart getCart(String sessionID){
        return cartsInMemory.get(sessionID);
    }
}

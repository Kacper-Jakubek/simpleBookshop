package pl.sdacademy.bookstore.cache;

import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.util.HashMap;
import java.util.Map;

public class CartCache {
    Map<String, Cart> cartsInMemory = new HashMap();
    private String sessionID;



    public boolean storeCart(Cart cart, String sessionID){
        cartsInMemory.put(sessionID,cart);
        return cartsInMemory.get(sessionID).equals(cart);
    }
    public void addNewProductToCart(String userCookie, OrderLine product){
        Cart cart = cartsInMemory.get(userCookie);
        cart.addProduct(product);
        cartsInMemory.replace(userCookie,cart);
    }
    public void removeProductFromCart(String userCookie, OrderLine product){
        Cart cart = cartsInMemory.get(userCookie);
        cart.removeProduct(product);
        if(cart.checkIfNextProductExists(product)){
            int idCount=0;
            for (OrderLine o:cart.getListofProducts()) {
                o.setId(idCount++);
            }
        }
            cartsInMemory.replace(userCookie,cart);
    }
}

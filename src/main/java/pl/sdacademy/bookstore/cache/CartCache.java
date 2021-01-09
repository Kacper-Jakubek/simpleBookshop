package pl.sdacademy.bookstore.cache;

import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.util.HashMap;
import java.util.Map;

public class CartCache {
    Map<String, Cart> CartsInMemory = new HashMap();
    private String sessionID;



    public boolean storeCart(Cart cart, String sessionID){
        CartsInMemory.put(sessionID,cart);
        return CartsInMemory.get(sessionID) == cart;
    }
    public void addNewProductToCart(String userCookie, OrderLine product){
        Cart cart = CartsInMemory.get(userCookie);
        cart.addProduct(product);
        CartsInMemory.replace(userCookie,cart);
    }
    public void removeProductFromCart(String userCookie, int id){
        Cart cart = CartsInMemory.get(userCookie);
        cart.removeProduct(id);
        if(cart.checkIfNextProductExists(id+1)){
            int idCount=0;
            for (OrderLine o:cart.getListofProducts()) {
                o.setId(idCount++);
            }
        }
            CartsInMemory.replace(userCookie,cart);
    }
}

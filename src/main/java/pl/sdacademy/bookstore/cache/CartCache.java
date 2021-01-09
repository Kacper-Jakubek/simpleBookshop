package pl.sdacademy.bookstore.cache;

import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.util.HashMap;
import java.util.Map;

public class CartCache {
    Map<Integer, Cart> CartsInMemory = new HashMap();
    private static int cacheCount;



    public Map<Integer, Cart> storeCart(Cart cart){
        CartsInMemory.put(cacheCount++,cart);
        return CartsInMemory;
    }
    public void addNewProductToCart(int userCookie, OrderLine product){
        Cart cart = CartsInMemory.get(userCookie);
        cart.addProduct(product);
        CartsInMemory.replace(userCookie,cart);
    }
    public void removeProductFromCart(int userCookie, int id){
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

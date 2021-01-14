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
    public void updateProductInCart(String userCookie, OrderLine product){
        Cart cart = cartsInMemory.get(userCookie);
        cart.updateProduct(product);
    }
    public boolean checkIfCartExist(String userCookie){
        return cartsInMemory.get(userCookie)!=null;
    }

    public int getNextId(){
        return cartsInMemory.size();
    }

    public OrderLine getOrderLine(String userCookie, ProductDTO productDTO){
        Cart cart = cartsInMemory.get(userCookie);
        ArrayList<OrderLine> orderLines = cart.getListofProducts();
        for (OrderLine o:orderLines) {
            if(o.getProduct().equals(productDTO)){
                return o;
            }
        }
    return null;
    }
    public Cart getCart(String userCookie){
        return cartsInMemory.get(userCookie);
    }
}

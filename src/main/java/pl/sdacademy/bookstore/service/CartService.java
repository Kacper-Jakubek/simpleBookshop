package pl.sdacademy.bookstore.service;

import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.cache.CartCache;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.util.ArrayList;

@Service
public class CartService {
    private final CartCache cartCache;

    public CartService(CartCache cartCache) {
        this.cartCache = cartCache;
    }

    public void validateCart(String cookie){
        if(!cartCache.checkIfCartExist(cookie)){
            Cart cart = new Cart();
            cartCache.storeCart(cart,cookie);
        }
    }

    public OrderLine add(ProductDTO productDTO, String sessionID){
        validateCart(sessionID);
        OrderLine orderLine = new OrderLine(cartCache.getNextId(),productDTO,1,productDTO.getPrice(),productDTO.getPrice());
        cartCache.addNewProductToCart(sessionID,orderLine);
        return orderLine;
    }

    public boolean increaseQuantity(ProductDTO productDTO, String sessionID){
        validateCart(sessionID);
        OrderLine orderLine = cartCache.getOrderLine(sessionID,productDTO);
        int oldQuantity = orderLine.getQuantity();
        orderLine.setQuantity(oldQuantity+1);
        cartCache.updateProductInCart(sessionID,orderLine);
        return orderLine.getQuantity() != oldQuantity;
        }

    public boolean decreaseQuantity(ProductDTO productDTO, String sessionID){
        validateCart(sessionID);
        OrderLine orderLine = cartCache.getOrderLine(sessionID,productDTO);
        int oldQuantity = orderLine.getQuantity();
        orderLine.setQuantity(oldQuantity-1);
        if(orderLine.getQuantity()<=0){
            cartCache.removeProductFromCart(sessionID,orderLine);
            return false;
        }else {
            cartCache.updateProductInCart(sessionID,orderLine);
            return true;
        }
    }

    public boolean delete(ProductDTO productDTO, String sessionID){
        validateCart(sessionID);
        OrderLine orderLine = cartCache.getOrderLine(sessionID,productDTO);
        if(orderLine!=null) {
            cartCache.removeProductFromCart(sessionID, orderLine);
            return true;
        }else {
            return false;
        }
    }
    public ArrayList<OrderLine> findAllProducts(String sessionID){
        validateCart(sessionID);
        Cart cart = cartCache.getCart(sessionID);
        return cart.getListofProducts();
    }

}

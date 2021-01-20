package pl.sdacademy.bookstore.service;

import liquibase.pro.packaged.C;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.cache.CartCache;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class CartService implements ICartService{
    private final CartCache cartCache;

    public CartService(CartCache cartCache) {
        this.cartCache = cartCache;
    }


    @Override
    public void validateCart(String cookie){
        if(!cartCache.checkIfCartExist(cookie)){
            LocalTime creationTime = LocalTime.now();
            Cart cart = new Cart(creationTime);
            cartCache.storeCart(cart,cookie);
        }
    }

    @Override
    public OrderLine add(ProductDTO productDTO, String cookie){
        validateCart(cookie);
        OrderLine orderLine = new OrderLine(cartCache.getNextId(),productDTO,1,productDTO.getPrice(),productDTO.getPrice());
        cartCache.addNewProductToCart(cookie,orderLine);
        return orderLine;
    }

    @Override
    public OrderLine increaseQuantity(ProductDTO productDTO, String cookie){
        validateCart(cookie);
        OrderLine orderLine = cartCache.getOrderLine(cookie,productDTO);
        int oldQuantity = orderLine.getQuantity();
        orderLine.setQuantity(oldQuantity+1);
        cartCache.updateProductInCart(cookie,orderLine);
        return orderLine;
    }

    @Override
    public OrderLine decreaseQuantity(ProductDTO productDTO, String cookie){
        validateCart(cookie);
        OrderLine orderLine = cartCache.getOrderLine(cookie,productDTO);
        int oldQuantity = orderLine.getQuantity();
        orderLine.setQuantity(oldQuantity-1);
        if(orderLine.getQuantity()<=0){
            cartCache.removeProductFromCart(cookie,orderLine);
            return null;
        }else {
            cartCache.updateProductInCart(cookie,orderLine);
            return orderLine;
        }
    }

    @Override
    public boolean delete(ProductDTO productDTO, String cookie){
        validateCart(cookie);
        OrderLine orderLine = cartCache.getOrderLine(cookie,productDTO);
        if(orderLine!=null) {
            cartCache.removeProductFromCart(cookie, orderLine);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ArrayList<OrderLine> findAllProducts(String cookie){
        validateCart(cookie);
        Cart cart = cartCache.getCart(cookie);
        return cart.getListofProducts();
    }

    @Override
    public int clean() {
        Map<String, Cart> carts = cartCache.getCartsInMemory();
        Iterator<Cart> it = carts.values().iterator();
        int counter = 0;

        while (it.hasNext())
        {
            LocalTime cleaningTime = it.next().getDeleteTime();
            if (cleaningTime.isBefore(LocalTime.now())){
                it.remove();
                counter++;
            }
        }
        return counter;
    }
}

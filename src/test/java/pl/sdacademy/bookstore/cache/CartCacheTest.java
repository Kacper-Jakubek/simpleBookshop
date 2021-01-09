package pl.sdacademy.bookstore.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.DTO.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CartCacheTest {



    @Test
    void shouldReturn2Carts() {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        CartCache cartCache = new CartCache();

        cartCache.storeCart(cart1);
        cartCache.storeCart(cart2);

        assertThat(cartCache.CartsInMemory.size()).isEqualTo(2);

    }
    @Test
    void shouldReturn2CartsWith2Products() {
        //given
        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        OrderLine orderLine1 = new OrderLine(0, productDTO1,2,50,50);
        OrderLine orderLine2 = new OrderLine(1, productDTO2,1,50,50);
        OrderLine orderLine3 = new OrderLine(2, productDTO2,4,50,50);
        OrderLine orderLine4 = new OrderLine(3, productDTO1,2,50,50);
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        CartCache cartCache = new CartCache();
        cartCache.storeCart(cart1);
        cartCache.storeCart(cart2);
        //when
        cartCache.addNewProductToCart(0,orderLine1);
        cartCache.addNewProductToCart(0,orderLine2);
        cartCache.addNewProductToCart(1,orderLine3);
        cartCache.addNewProductToCart(1,orderLine4);
        //then
        assertThat(cartCache.CartsInMemory.size()).isEqualTo(2);
        assertThat(cart1.getListofProducts().size()).isEqualTo(2);
        assertThat(cart2.getListofProducts().size()).isEqualTo(2);
    }
    @Test
    void shouldRemoveLastProduct(){
        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        OrderLine orderLine1 = new OrderLine(0, productDTO1,2,50,50);
        OrderLine orderLine2 = new OrderLine(1, productDTO2,1,50,50);
        OrderLine orderLine3 = new OrderLine(2, productDTO2,4,50,50);
        Cart cart1 = new Cart();
        CartCache cartCache = new CartCache();

        cartCache.storeCart(cart1);
        cartCache.addNewProductToCart(0,orderLine1);
        cartCache.addNewProductToCart(0,orderLine2);
        cartCache.addNewProductToCart(0,orderLine3);
        cartCache.removeProductFromCart(0,2);

        assertThat(cartCache.CartsInMemory.size()).isEqualTo(1);
        assertThat(cart1.getListofProducts().size()).isEqualTo(2);
        assertThat(cart1.getListofProducts().get(0)).isEqualTo(orderLine1);
        assertThat(cart1.getListofProducts().get(1)).isEqualTo(orderLine2);
    }
    @Test
    void shouldRemoveMiddleProduct(){
        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        OrderLine orderLine1 = new OrderLine(0, productDTO1,2,50,50);
        OrderLine orderLine2 = new OrderLine(1, productDTO2,1,50,50);
        OrderLine orderLine3 = new OrderLine(2, productDTO2,4,50,50);
        Cart cart1 = new Cart();
        CartCache cartCache = new CartCache();

        cartCache.storeCart(cart1);
        cartCache.addNewProductToCart(0,orderLine1);
        cartCache.addNewProductToCart(0,orderLine2);
        cartCache.addNewProductToCart(0,orderLine3);
        cartCache.removeProductFromCart(0,1);

        assertThat(cartCache.CartsInMemory.size()).isEqualTo(1);
        assertThat(cart1.getListofProducts().size()).isEqualTo(2);
        assertThat(cart1.getListofProducts().get(0)).isEqualTo(orderLine1);
        assertThat(cart1.getListofProducts().get(0).getId()).isEqualTo(0);
        assertThat(cart1.getListofProducts().get(1)).isEqualTo(orderLine3);
        assertThat(cart1.getListofProducts().get(1).getId()).isEqualTo(1);
    }


}
package pl.sdacademy.bookstore.cache;

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import static org.assertj.core.api.Assertions.assertThat;

class CartCacheTest {



    @Test
    void shouldStore2Carts() {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        CartCache cartCache = new CartCache();

        cartCache.storeCart(cart1,"1");
        cartCache.storeCart(cart2,"2");

        assertThat(cartCache.cartsInMemory.size()).isEqualTo(2);

    }
    @Test
    void shouldStore2CartsWith2Products() {
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
        cartCache.storeCart(cart1,"1");
        cartCache.storeCart(cart2,"2");
        //when
        cartCache.addNewProductToCart("1",orderLine1);
        cartCache.addNewProductToCart("1",orderLine2);
        cartCache.addNewProductToCart("2",orderLine3);
        cartCache.addNewProductToCart("2",orderLine4);
        //then
        assertThat(cartCache.cartsInMemory.size()).isEqualTo(2);
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

        cartCache.storeCart(cart1,"1");
        cartCache.addNewProductToCart("1",orderLine1);
        cartCache.addNewProductToCart("1",orderLine2);
        cartCache.addNewProductToCart("1",orderLine3);
        cartCache.removeProductFromCart("1",orderLine3);

        assertThat(cartCache.cartsInMemory.size()).isEqualTo(1);
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

        cartCache.storeCart(cart1,"1");
        cartCache.addNewProductToCart("1",orderLine1);
        cartCache.addNewProductToCart("1",orderLine2);
        cartCache.addNewProductToCart("1",orderLine3);
        cartCache.removeProductFromCart("1",orderLine2);

        assertThat(cartCache.cartsInMemory.size()).isEqualTo(1);
        assertThat(cart1.getListofProducts().size()).isEqualTo(2);
        assertThat(cart1.getListofProducts().get(0)).isEqualTo(orderLine1);
        assertThat(cart1.getListofProducts().get(0).getId()).isEqualTo(0);
        assertThat(cart1.getListofProducts().get(1)).isEqualTo(orderLine3);
        assertThat(cart1.getListofProducts().get(1).getId()).isEqualTo(1);
    }

    @Test
    void shouldReturnLackOfCart(){
        CartCache cartCache = new CartCache();
        Cart cart1 = new Cart();
        cartCache.storeCart(cart1,"0");
        assertThat(cartCache.checkIfCartExist("1")).isEqualTo(false);
        assertThat(cartCache.checkIfCartExist("0")).isEqualTo(true);
    }


}
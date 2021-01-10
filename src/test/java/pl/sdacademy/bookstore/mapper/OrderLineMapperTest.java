package pl.sdacademy.bookstore.mapper;

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.dto.ShoppingCart;
import pl.sdacademy.bookstore.model.OrderLine;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineMapperTest {
    @Test
    public void shouldMapOrderLineToDto() {
        //given
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setName("Mappers are good");
        OrderLine orderLine = new OrderLine(1, productDTO1,2,50,50);

        //when
        ShoppingCart shoppingCart = OrderLineMapper.INSTANCE.mapToOrderLineDTO( orderLine );

        //then
        assertThat( shoppingCart ).isNotNull();
        assertThat( shoppingCart.getBookName() ).isEqualTo( "Mappers are good" );
        assertThat( shoppingCart.getHowMany() ).isEqualTo( 2 );
        assertThat( shoppingCart.getBookPrice() ).isEqualTo( 50 );
    }
}
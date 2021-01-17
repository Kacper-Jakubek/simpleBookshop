package pl.sdacademy.bookstore.mapper;

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.db.OrderLineEntity;
import pl.sdacademy.bookstore.dto.ProductDTO;
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
        OrderLineEntity orderLineEntity = OrderLineMapper.INSTANCE.mapToOrderLineEntity((OrderLine) orderLine);

        //then
        assertThat( orderLineEntity ).isNotNull();
        assertThat( orderLineEntity.getBookName() ).isEqualTo( "Mappers are good" );
        assertThat( orderLineEntity.getQuantity() ).isEqualTo( 2 );
        assertThat( orderLineEntity.getPrice() ).isEqualTo( 50 );
    }
}
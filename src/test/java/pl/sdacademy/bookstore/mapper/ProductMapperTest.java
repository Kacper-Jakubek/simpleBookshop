package pl.sdacademy.bookstore.mapper;

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.db.AuthorEntity;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.db.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    @Test
    public void shouldMapProductToDto() {
        //given
        Set<CategoryEntity> categories = null;
        List<AuthorEntity> authors = null;
        ProductEntity productEntity = new ProductEntity(1,"Wiedźmin","opis", BigDecimal.ONE,true,"URL",categories,authors);

        //when
        ProductDTO productDTO = ProductMapper.INSTANCE.mapToProductDTO( productEntity );

        //then
        assertThat(productDTO).isNotNull();
        assertThat( productDTO.getName() ).isEqualTo( "Wiedźmin" );
        assertThat( productDTO.getPrice() ).isEqualTo( 1.0 );
    }

}
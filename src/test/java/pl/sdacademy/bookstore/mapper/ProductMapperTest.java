//package pl.sdacademy.bookstore.mapper;
//
//import org.junit.jupiter.api.Test;
//import pl.sdacademy.bookstore.DTO.ProductDTO;
//import pl.sdacademy.bookstore.db.ProductEntity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ProductMapperTest {
//    @Test
//    public void shouldMapCarToDto() {
//        //given
//        ProductEntity productEntity = new ProductEntity("IT");
//
//        //when
//        ProductDTO productDTO = ProductMapper.INSTANCE.mapToProductDTO( productEntity );
//
//        //then
//        assertThat(productDTO).isNotNull();
//        assertThat( productDTO.getName() ).isEqualTo( "IT" );
//    }
//
//}
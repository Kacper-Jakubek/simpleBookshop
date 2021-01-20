package pl.sdacademy.bookstore.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.db.ProductEntity;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    @Mapping(source = "title", target = "name")
    ProductDTO mapToProductDTO(ProductEntity product);
}

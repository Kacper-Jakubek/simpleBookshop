package pl.sdacademy.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sdacademy.bookstore.dto.ShoppingCart;
import pl.sdacademy.bookstore.model.OrderLine;

@Mapper(uses = {ProductNameMapper.class})
public interface OrderLineMapper {
    OrderLineMapper INSTANCE = Mappers.getMapper( OrderLineMapper.class );

    @Mapping(source = "product", target = "bookName")
    @Mapping(source = "quantity", target = "howMany")
    @Mapping(source = "price", target = "bookPrice")
    ShoppingCart mapToOrderLineDTO(OrderLine orderLine);

}

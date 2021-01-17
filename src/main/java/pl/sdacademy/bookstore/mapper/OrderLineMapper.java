package pl.sdacademy.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sdacademy.bookstore.db.OrderLineEntity;
import pl.sdacademy.bookstore.model.OrderLine;

@Mapper(uses = {ProductNameMapper.class})
public interface OrderLineMapper {
    OrderLineMapper INSTANCE = Mappers.getMapper( OrderLineMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "bookName")
    @Mapping(source = "price", target = "price")
    @Mapping(ignore = true, target = "createOrderLine")
    OrderLineEntity mapToOrderLineEntity(OrderLine orderLine);


}

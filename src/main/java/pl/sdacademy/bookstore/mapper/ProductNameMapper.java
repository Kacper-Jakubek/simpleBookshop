package pl.sdacademy.bookstore.mapper;

import pl.sdacademy.bookstore.DTO.ProductDTO;

public class ProductNameMapper {

    String ProductNameToString(ProductDTO productDTO) {
        return productDTO.getName();
    }
}

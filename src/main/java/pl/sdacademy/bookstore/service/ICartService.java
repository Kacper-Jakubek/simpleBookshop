package pl.sdacademy.bookstore.service;

import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.Cart;
import pl.sdacademy.bookstore.model.OrderLine;

import java.time.LocalTime;
import java.util.ArrayList;

public interface ICartService {

    void validateCart(String cookie);

    OrderLine add(ProductDTO productDTO, String cookie);

    OrderLine increaseQuantity(ProductDTO productDTO, String cookie);

    OrderLine decreaseQuantity(ProductDTO productDTO, String cookie);

    boolean delete(ProductDTO productDTO, String cookie);

    ArrayList<OrderLine> findAllProducts(String cookie);

    int clean();
}

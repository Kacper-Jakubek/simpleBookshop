package pl.sdacademy.bookstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.bookstore.dto.ProductDTO;
import pl.sdacademy.bookstore.model.OrderLine;
import pl.sdacademy.bookstore.service.CartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CartController {
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void createCartAndSetCookie(HttpServletResponse response) {
        Integer number = (int) Math.ceil(Math.random() * 100);
        String sessionID = number.toString();
        Cookie cookie = new Cookie("sessionID", sessionID);
        response.addCookie(cookie);
        LOG.info("setCookie");
    }

    @GetMapping()
    public List<OrderLine> get(@CookieValue(value = "sessionID")String sessionID) {
        LOG.info("gotAllProducts");
        return cartService.findAllProducts(sessionID);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping()
    public String add(@CookieValue(value = "sessionID")String sessionID, @RequestBody ProductDTO productDTO) {
        cartService.add(productDTO,sessionID);
        LOG.info("added product {}", productDTO.getName());
        return "new product in cart";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/increase")
    public String increaseQuantity(@CookieValue(value = "sessionID")String sessionID, @RequestBody ProductDTO productDTO) {
    cartService.increaseQuantity(productDTO,sessionID);
    LOG.info("changed quantity of {}", productDTO.getName());
    return "updated";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/decrease")
    public String decreaseQuantity(@CookieValue(value = "sessionID")String sessionID, @RequestBody ProductDTO productDTO) {
    cartService.decreaseQuantity(productDTO,sessionID);
    LOG.info("changed quantity of {}", productDTO.getName());
    return "updated";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public void delete(@CookieValue(value = "sessionID")String sessionID, @RequestBody ProductDTO productDTO) {
        cartService.delete(productDTO,sessionID);
        LOG.info("product {} deleted", productDTO.getName());
    }
}

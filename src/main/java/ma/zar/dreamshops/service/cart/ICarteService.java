package ma.zar.dreamshops.service.cart;

import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICarteService {
    Cart getCart(Long id) throws ResourceNotFoundException;
    void clearCart(Long id) throws ResourceNotFoundException;
    BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException;

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}

package ma.zar.dreamshops.service.cart;

import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;
import ma.zar.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id) throws ResourceNotFoundException;
    void clearCart(Long id) throws ResourceNotFoundException;
    BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException;

    Cart initializeNewCart(User user) throws ResourceNotFoundException;

    Cart getCartByUserId(Long userId);
}

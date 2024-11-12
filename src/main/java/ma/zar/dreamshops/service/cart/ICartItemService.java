package ma.zar.dreamshops.service.cart;

import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity) throws ResourceNotFoundException;
    void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException;
    void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException;

    CartItem getCartItem(Long cartId, Long productId) throws ResourceNotFoundException;
}

package ma.zar.dreamshops.service.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;
import ma.zar.dreamshops.model.User;
import ma.zar.dreamshops.repository.CartItemRepository;
import ma.zar.dreamshops.repository.CarteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CarteRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }


    @Transactional
    @Override
    public void clearCart(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(Math.toIntExact(id));
        cart.getItems().clear();
        cartRepository.deleteById(Math.toIntExact(id));

    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                })  ;

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

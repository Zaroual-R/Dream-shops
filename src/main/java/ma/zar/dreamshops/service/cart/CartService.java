package ma.zar.dreamshops.service.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;
import ma.zar.dreamshops.repository.CartItemRepository;
import ma.zar.dreamshops.repository.CarteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICarteService{
    private final CarteRepository carteRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) throws ResourceNotFoundException {
        Cart cart = carteRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return carteRepository.save(cart);

    }

    @Transactional
    @Override
    public void clearCart(Long id) throws ResourceNotFoundException {
       Cart cart = getCart(id);
       cartItemRepository.deleteAllByCartId(Math.toIntExact(id));
       cart.getItems().clear();
       carteRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return carteRepository.save(newCart).getId();

    }
}

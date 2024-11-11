package ma.zar.dreamshops.service.cart;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;
import ma.zar.dreamshops.model.CartItem;
import ma.zar.dreamshops.model.Product;
import ma.zar.dreamshops.repository.CartItemRepository;
import ma.zar.dreamshops.repository.CarteRepository;
import ma.zar.dreamshops.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICarteService carteService;
    private final CarteRepository carteRepository;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) throws ResourceNotFoundException {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        Cart cart = carteService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        carteRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart=carteService.getCart(cartId);
        CartItem cartItemToRemove=cart.getItems().stream()
                .filter(item ->item.getProduct().getProductId().equals(productId))
                .findFirst().orElse(null);
        cart.removeItem(cartItemToRemove);
        carteRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException {
        Cart cart=carteService.getCart(cartId);
        cart.getItems().stream()
                .filter(item ->item.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(item ->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount=cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        carteRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart = carteService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item not found"));
    }
}

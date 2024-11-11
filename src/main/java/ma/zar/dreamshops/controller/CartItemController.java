package ma.zar.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.repository.CarteRepository;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.service.cart.ICartItemService;
import ma.zar.dreamshops.service.cart.ICarteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICarteService cartService;
    private final CarteRepository carteRepository;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponce> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
                cartId= cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponce("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponce> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponce("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponce> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponce("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce(e.getMessage(), null));
        }

    }
}

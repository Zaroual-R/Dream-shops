package ma.zar.dreamshops.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Cart;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.service.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
   private final CartService cartService;

   @GetMapping("/my-cart/{cartId}")
   public ResponseEntity<ApiResponce>  getCart(@PathVariable Long cartId) {
       try {
           Cart cart=cartService.getCart(cartId);
           return ResponseEntity.ok(new ApiResponce("success ",cart));
       } catch (ResourceNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponce("not found",null));
       }
   }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<ApiResponce> clearCart( @PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponce("Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponce> getTotalAmount( @PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponce("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce(e.getMessage(), null));
        }
    }


}

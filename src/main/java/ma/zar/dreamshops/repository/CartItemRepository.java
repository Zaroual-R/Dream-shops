package ma.zar.dreamshops.repository;

import ma.zar.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteAllByCartId(int id);
}

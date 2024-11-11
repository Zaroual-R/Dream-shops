package ma.zar.dreamshops.repository;

import ma.zar.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Long userId);
}

package ma.zar.dreamshops.repository;

import ma.zar.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByNameAndBrand(String brand, String name);

    int countByBrandAndName(String brand, String name);
}

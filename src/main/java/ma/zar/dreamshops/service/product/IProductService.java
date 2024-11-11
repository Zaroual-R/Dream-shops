package ma.zar.dreamshops.service.product;

import ma.zar.dreamshops.dtos.ProductRequest;
import ma.zar.dreamshops.model.Category;
import ma.zar.dreamshops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(ProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrande(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByBrandAndName(String brand,String name);
    int countProductByBrandAndName(String brand,String name);

}

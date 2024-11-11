package ma.zar.dreamshops.service.product;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.dtos.ProductRequest;
import ma.zar.dreamshops.exceptions.ProductNotFoundException;
import ma.zar.dreamshops.model.Category;
import ma.zar.dreamshops.model.Product;
import ma.zar.dreamshops.repository.CategoryRepository;
import ma.zar.dreamshops.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSevice implements  IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductRequest request) {
       Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
               .orElseGet(()->{
                   Category category1=new Category(request.getCategory().getName());
                   return categoryRepository.save(category1);
                       });
         request.setCategory(category);
         return productRepository.save(createProduct(request, category));

    }


    private Product createProduct(ProductRequest product,Category category) {
        return  Product.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .description(product.getDescription())
                .inventory(product.getInventory())
                .category(category)
                .build();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
      productRepository.findById(Math.toIntExact(id)).ifPresentOrElse(productRepository::delete,
              ()->{throw new ProductNotFoundException("product not found");
      });
    }

    @Override
    public Product updateProduct(ProductRequest request, Long id) {
        return productRepository.findById(Math.toIntExact(id))
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("product not found"));

    }

    private Product updateExistingProduct(Product Exisitingproduct, ProductRequest productRequest) {
        Exisitingproduct.setName(productRequest.getName());
        Exisitingproduct.setBrand(productRequest.getBrand());
        Exisitingproduct.setPrice(productRequest.getPrice());
        Exisitingproduct.setDescription(productRequest.getDescription());
        Exisitingproduct.setInventory(productRequest.getInventory());
        Category category = categoryRepository.findByName(productRequest.getCategory().getName());
        Exisitingproduct.setCategory(category);

        return Exisitingproduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrande(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
       return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByNameAndBrand(brand,name);
    }

    @Override
    public int countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}

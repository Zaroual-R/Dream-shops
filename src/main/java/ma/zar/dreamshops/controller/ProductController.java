package ma.zar.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.dtos.ProductRequest;
import ma.zar.dreamshops.exceptions.ProductNotFoundException;
import ma.zar.dreamshops.model.Product;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.service.product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final IProductService productService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponce> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            return  ResponseEntity.ok(new ApiResponce("upload success!",product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("upload failed!",e.getMessage()));
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponce> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return  ResponseEntity.ok(new ApiResponce(" success!",product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce(" failed!",e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponce> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return  ResponseEntity.ok(new ApiResponce("delete  success!",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("delete failed!",e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponce> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable Long id) {
        try {
            Product product=productService.updateProduct(productRequest,id);
            return  ResponseEntity.ok(new ApiResponce("update  success!",product));
        } catch (ProductNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("update failed!",e.getMessage()));
        }
    }

     @GetMapping("/all")
     public ResponseEntity<ApiResponce> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponce("success!",products));
    }

    @GetMapping("/all/by-category/{category}")
    public ResponseEntity<ApiResponce> getProductByCategory(@PathVariable String category){
        try {
            List<Product> productsOfSameCategory=productService.getProductsByCategory(category);
            if(productsOfSameCategory.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("not product found!",null));
            }
            return ResponseEntity.ok(new ApiResponce("success!",productsOfSameCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("server error!",e.getMessage()));
        }
    }

    @GetMapping("/all/by-brand/{brand}")
    public ResponseEntity<ApiResponce> getProductByBrand(@PathVariable  String brand){
        try {
            List<Product> productsOfSameBrand=productService.getProductsByBrande(brand);
            if(productsOfSameBrand.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("not product found!",null));
            }
            return ResponseEntity.ok(new ApiResponce("success!",productsOfSameBrand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("server error!",e.getMessage()));
        }
    }

    @GetMapping("/all/by-name/{name}")
    public ResponseEntity<ApiResponce> getProductByName(@PathVariable  String name){
        try {
            List<Product> products=productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("not product found!",null));
            }
            return ResponseEntity.ok(new ApiResponce("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("server error!",e.getMessage()));
        }
    }

    @GetMapping("/all/by-gategroy-brand/{category}/{brand}")
    public ResponseEntity<ApiResponce> getProductByCategoryAndBrand(@PathVariable String category,@PathVariable String brand){
        try {
            List<Product> productOfSamecatAndBrand=productService.getProductsByCategoryAndBrand(category,brand);
            if(productOfSamecatAndBrand.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("not product found!",null));
            }
            return ResponseEntity.ok(new ApiResponce("success!",productOfSamecatAndBrand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("server error!",e.getMessage()));
        }
    }

    @GetMapping("/all/by-brand-name/{brand}/{name}")
    public ResponseEntity<ApiResponce> getProductByBrandAndName(@PathVariable String brand,@PathVariable String name){
        try {
            List<Product> productOfSameNameAndBrand=productService.getProductsByBrandAndName(brand,name);
            if(productOfSameNameAndBrand.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce("not product found!",null));
            }
            return ResponseEntity.ok(new ApiResponce("success!",productOfSameNameAndBrand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponce("server error!",e.getMessage()));
        }
    }

    @GetMapping("/count-brand-name/{name}/{brand}")
    public ResponseEntity<ApiResponce> countProductByNameAndBrand(@PathVariable String name,@PathVariable String brand){
        int nbrOfProdut=productService.countProductByBrandAndName(name,brand);
        return ResponseEntity.ok(new ApiResponce("success!",nbrOfProdut));
    }





}

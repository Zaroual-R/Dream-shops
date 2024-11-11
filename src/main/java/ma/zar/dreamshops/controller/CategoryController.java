package ma.zar.dreamshops.controller;


import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.CategoryNotFoundException;
import ma.zar.dreamshops.exceptions.ResourceAlreadyExistException;
import ma.zar.dreamshops.model.Category;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.service.category.CategoryService;
import ma.zar.dreamshops.service.category.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService  categoryService ;

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponce> addCategory(@RequestBody Category Category) {
        try {
            Category category=categoryService.addCategory(Category);
            return ResponseEntity.ok(new ApiResponce("upload success!",category));
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponce(e.getMessage(),null));
        }
    }

    @GetMapping("/category/all")
    public ResponseEntity<ApiResponce> getAllCategories() {
        try {
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponce("found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponce("failed!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponce> getCategoryById(@PathVariable  Long id) {
        try {
            Category category=categoryService.findCategoryById(id);
            return ResponseEntity.ok(new ApiResponce("get success!",category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponce> getCategoryByName(@PathVariable  String name) {
        try {
            Category category=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponce("success!",category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponce(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ApiResponce> deteteCategoryById(@PathVariable  Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponce("detlet success!",null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce(e.getMessage(),null));
        }
    }


    @PutMapping("/category/update/{id}")
    public ResponseEntity<ApiResponce> updateCategory(@RequestBody Category Category,@PathVariable  Long id) {
        try {
            Category category=categoryService.updateCategory(Category,id);
            return ResponseEntity.ok(new ApiResponce("update success!",category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponce(e.getMessage(),null));
        }
    }






}

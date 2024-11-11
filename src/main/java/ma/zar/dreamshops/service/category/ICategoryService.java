package ma.zar.dreamshops.service.category;

import ma.zar.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category findCategoryById(Long id);
    List<Category> getAllCategories();
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long id);

}

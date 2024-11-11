package ma.zar.dreamshops.service.category;


import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.exceptions.CategoryNotFoundException;
import ma.zar.dreamshops.exceptions.ResourceAlreadyExistException;
import ma.zar.dreamshops.model.Category;
import ma.zar.dreamshops.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService  implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(Math.toIntExact(id)).orElseThrow(
                ()->new CategoryNotFoundException("category not fout")
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        if(!categoryRepository.existsByName(name)){
            throw new CategoryNotFoundException("the category with name"+name+"is not exist");
        }
        return  categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())){
            throw new ResourceAlreadyExistException("the category with name"+category.getName()+"is already exist");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(findCategoryById(id)).map(oldCategory ->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()->
            new CategoryNotFoundException("category not fout")
        );
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(Math.toIntExact(id)).ifPresentOrElse(categoryRepository::delete,()->{
            throw new CategoryNotFoundException("category not fout");
        });
    }
}

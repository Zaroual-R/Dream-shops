package ma.zar.dreamshops.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryNotFout) {
        super(categoryNotFout);
    }
}

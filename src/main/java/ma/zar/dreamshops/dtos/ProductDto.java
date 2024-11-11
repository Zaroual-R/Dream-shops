package ma.zar.dreamshops.dtos;

import lombok.Builder;
import lombok.Data;
import ma.zar.dreamshops.model.Category;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private Long product_id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDto> images;
}

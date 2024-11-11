package ma.zar.dreamshops.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import ma.zar.dreamshops.model.Category;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private Long product_id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
}

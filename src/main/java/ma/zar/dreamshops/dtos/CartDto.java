package ma.zar.dreamshops.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private Set<CarteItemDto> items;
    private BigDecimal totalAmount;
}

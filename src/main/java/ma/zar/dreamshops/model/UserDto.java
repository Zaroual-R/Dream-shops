package ma.zar.dreamshops.model;

import lombok.Data;
import ma.zar.dreamshops.dtos.CartDto;
import ma.zar.dreamshops.dtos.OrderDto;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}

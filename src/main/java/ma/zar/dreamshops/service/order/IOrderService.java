package ma.zar.dreamshops.service.order;

import ma.zar.dreamshops.dtos.OrderDto;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId) throws ResourceNotFoundException;
    OrderDto getOrder(Long orderId) throws ResourceNotFoundException;
    List<OrderDto> getUserOrders(Long userId);
}

package kitchenpos.dao;

import kitchenpos.domain.OrderLineItem;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface OrderLineItemRepository extends Repository<OrderLineItem, Long> {
    OrderLineItem save(OrderLineItem entity);

//    Optional<OrderLineItem> findBySeq(Long id);
//
//    List<OrderLineItem> findAll();

//    List<OrderLineItem> findAllByOrderId(Long orderId);
}

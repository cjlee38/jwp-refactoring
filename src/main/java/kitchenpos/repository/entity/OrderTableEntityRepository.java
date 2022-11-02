package kitchenpos.repository.entity;

import java.util.Collection;
import java.util.List;
import kitchenpos.domain.table.OrderTable;

public interface OrderTableEntityRepository {
    OrderTable getById(Long id);

    List<OrderTable> getAllByIdIn(Collection<Long> ids);
}
package kitchenpos.dao;

import kitchenpos.domain.OrderTable;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface OrderTableDao extends Repository<OrderTable, Long> {
    OrderTable save(OrderTable entity);

    Optional<OrderTable> findById(Long id);

    List<OrderTable> findAll();

    List<OrderTable> findAllByIdIn(List<Long> ids);

    List<OrderTable> findAllByTableGroupId(Long tableGroupId);
}

package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import kitchenpos.support.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class TableGroupServiceTest {

    private TableGroupService tableGroupService;
    private OrderTableDao orderTableDao;
    private OrderDao orderDao;

    @Autowired
    public TableGroupServiceTest(TableGroupService tableGroupService, OrderTableDao orderTableDao, OrderDao orderDao) {
        this.tableGroupService = tableGroupService;
        this.orderTableDao = orderTableDao;
        this.orderDao = orderDao;
    }

    @Test
    void create() {
        // given
        List<OrderTable> orderTables = List.of(new OrderTable(1L, 1L, 2, false), new OrderTable(2L, null, 0, true));
        TableGroup tableGroup = new TableGroup(LocalDateTime.now(), orderTables);

        // when
        TableGroup createdTableGroup = tableGroupService.create(tableGroup);

        // then
        List<Long> expectedTableGroupIds = List.of(createdTableGroup.getId(), createdTableGroup.getId());
        List<Boolean> expectedEmpties = List.of(false, false);

        Assertions.assertAll(
                () -> assertThat(createdTableGroup.getId()).isNotNull(),
                () -> assertThat(orderTables).extracting("tableGroupId").containsAll(expectedTableGroupIds),
                () -> assertThat(orderTables).extracting("empty").containsAll(expectedEmpties)
        );
    }

    @Test
    void createWithEmptyOrderTable() {
        // given
        TableGroup tableGroup = new TableGroup(LocalDateTime.now(), new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createWithOneOrderTable() {
        // given
        TableGroup tableGroup = new TableGroup(
                LocalDateTime.now(),
                List.of(new OrderTable(1L, null, 0, true))
        );

        // when & then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createWithWrongOrderTable() {
        // given
        long wrongOrderTableId = 999L;
        TableGroup tableGroup = new TableGroup(
                LocalDateTime.now(),
                List.of(new OrderTable(1L, null, 0, true), new OrderTable(wrongOrderTableId, null, 0, true))
        );

        // when & then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createWithNotEmptyOrderTable() {
        // given
        OrderTable emptyOrderTable = orderTableDao.save(new OrderTable(1L, null, 2, false));
        TableGroup tableGroup = new TableGroup(
                LocalDateTime.now(),
                List.of(emptyOrderTable, new OrderTable(2L, null, 0, true))
        );

        // when & then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ungroup() {
        // given
        TableGroup tableGroup = new TableGroup(
                LocalDateTime.now(),
                List.of(new OrderTable(1L, null, 0, true), new OrderTable(2L, null, 0, true))
        );
        TableGroup createdTableGroup = tableGroupService.create(tableGroup);

        // when & then
        assertThatCode(() -> tableGroupService.ungroup(createdTableGroup.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void ungroupWithCookingStatus() {
        // given
        long orderTableId = 1L;
        Order order = new Order(orderTableId, OrderStatus.COOKING.name(), LocalDateTime.now(), null);
        orderDao.save(order);

        TableGroup tableGroup = new TableGroup(
                LocalDateTime.now(),
                List.of(new OrderTable(orderTableId, null, 0, true), new OrderTable(2L, null, 0, true))
        );
        TableGroup createdTableGroup = tableGroupService.create(tableGroup);

        // when & then
        assertThatThrownBy(() -> tableGroupService.ungroup(createdTableGroup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

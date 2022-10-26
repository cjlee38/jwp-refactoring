package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.TableGroupFixtures;
import kitchenpos.application.dto.OrderTableCreateRequest;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableRepository;
import kitchenpos.dao.TableGroupRepository;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import kitchenpos.support.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class TableServiceTest {

    private TableService tableService;
    private OrderDao orderDao;
    private OrderTableRepository orderTableRepository;
    private TableGroupRepository tableGroupRepository;

    @Autowired
    public TableServiceTest(
            TableService tableService,
            OrderDao orderDao,
            OrderTableRepository orderTableRepository,
            TableGroupRepository tableGroupRepository
    ) {
        this.tableService = tableService;
        this.orderDao = orderDao;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Test
    void create() {
        // given
        OrderTableCreateRequest request = new OrderTableCreateRequest(3, true);
        // when
        OrderTable createdOrderTable = tableService.create(request);
        // then
        assertThat(createdOrderTable.getId()).isNotNull();
    }

    @Test
    void list() {
        // given & when
        List<OrderTable> orderTables = tableService.list();
        // then
        int defaultSize = 8;
        assertThat(orderTables).hasSize(defaultSize);
    }

    @Test
    void changeEmpty() {
        // given
        OrderTable orderTable = tableService.create(new OrderTableCreateRequest(0, true));

        // when
        OrderTable changedOrderTable = tableService.changeEmpty(orderTable.getId(), false);

        // then
        assertThat(changedOrderTable.isEmpty()).isFalse();
    }

    @Test
    void changeEmptyWithTableGroupId() {
        // given
        TableGroup tableGroup = tableGroupRepository.save(TableGroupFixtures.createTableGroup());
        OrderTable orderTable = orderTableRepository.save(new OrderTable(tableGroup, 3, false));

        // when & then
        assertThatThrownBy(() -> tableService.changeEmpty(orderTable.getId(), true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeEmptyWithCookingStatus() {
        // given
        long orderTableId = 1L;
        Order order = new Order(orderTableId, OrderStatus.COOKING.name(), LocalDateTime.now(), null);
        orderDao.save(order);
        // when & then
        assertThatThrownBy(() -> tableService.changeEmpty(orderTableId, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeNumberOfGuests() {
        // given
        OrderTable orderTable = tableService.create(new OrderTableCreateRequest(2, false));

        // when
        OrderTable changedOrderTable = tableService.changeNumberOfGuests(orderTable.getId(), 4);

        // then
        assertThat(changedOrderTable.getNumberOfGuests()).isEqualTo(4);
    }

    @Test
    void changeNumberOfGuestsWithNegativeNumber() {
        // given
        OrderTable orderTable = tableService.create(new OrderTableCreateRequest(2, false));

        // when & then
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTable.getId(), -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeNumberOfGuestsWithEmpty() {
        // given
        OrderTable orderTable = tableService.create(new OrderTableCreateRequest(0, true));

        // when & then
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTable.getId(), 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeNumberOfGuestsWithInvalidOrderTableId() {
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(999L, 2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

package kitchenpos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.domain.order.OrderLineItem;
import org.junit.jupiter.api.Test;

class OrderLineItemTest {

    @Test
    void createOrderLineItem() {
        // given
        long quantity = 3L;
        long menuId = 1L;
        // when
        OrderLineItem orderLineItem = new OrderLineItem(menuId, quantity);
        // then
        assertThat(orderLineItem).isNotNull();
    }
}

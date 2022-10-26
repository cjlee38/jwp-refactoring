//package kitchenpos.dao;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import kitchenpos.domain.Order;
//import kitchenpos.domain.OrderLineItem;
//import kitchenpos.support.RepositoryTest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@RepositoryTest
//class OrderLineItemRepositoryTest {
//
//    private OrderLineItemRepository orderLineItemRepository;
//    private OrderRepository orderRepository;
//
//    @Autowired
//    public OrderLineItemRepositoryTest(OrderLineItemRepository orderLineItemRepository, OrderRepository orderRepository) {
//        this.orderLineItemRepository = orderLineItemRepository;
//        this.orderRepository = orderRepository;
//    }
//
//    private Long orderIdA;
//    private Long orderIdB;
//
//    @BeforeEach
//    void setUp() {
//        orderIdA = orderRepository.save(new Order(1L, "COOKING", LocalDateTime.now(), null)).getId();
//        orderIdB = orderRepository.save(new Order(2L, "COOKING", LocalDateTime.now(), null)).getId();
//    }
//
//    @Test
//    void save() {
//        // given
//        OrderLineItem orderLineItem = new OrderLineItem(orderIdA, 1L, 2);
//        // when
//        OrderLineItem savedOrderLineItem = orderLineItemRepository.save(orderLineItem);
//        // then
//        assertThat(savedOrderLineItem.getSeq()).isNotNull();
//    }
//
//    @Test
//    void findById() {
//        // given
//        OrderLineItem orderLineItem = new OrderLineItem(orderIdA, 1L, 2);
//        OrderLineItem savedOrderLineItem = orderLineItemRepository.save(orderLineItem);
//
//        // when
//        Optional<OrderLineItem> foundOrderLineItem = orderLineItemRepository.findBySeq(savedOrderLineItem.getSeq());
//
//        // then
//        assertThat(foundOrderLineItem).isPresent();
//    }
//
//    @Test
//    void findAll() {
//        // given
//        orderLineItemRepository.save(new OrderLineItem(orderIdA, 1L, 2));
//        orderLineItemRepository.save(new OrderLineItem(orderIdB, 2L, 3));
//
//        // when
//        List<OrderLineItem> orderLineItems = orderLineItemRepository.findAll();
//
//        // then
//        assertThat(orderLineItems).hasSize(2);
//    }
//}

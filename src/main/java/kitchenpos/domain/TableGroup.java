package kitchenpos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.util.CollectionUtils;

@Entity
public class TableGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "tableGroup", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<OrderTable> orderTables = new ArrayList<>();

    public TableGroup() {
    }

    public TableGroup(LocalDateTime createdDate, List<OrderTable> orderTables) {
        this(null, createdDate, orderTables);
    }

    public TableGroup(Long id, LocalDateTime createdDate, List<OrderTable> orderTables) {
        validateOrderTablesLength(orderTables);
        groupOrderTables(orderTables);
        this.id = id;
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    private void validateOrderTablesLength(List<OrderTable> orderTables) {
        if (CollectionUtils.isEmpty(orderTables) || orderTables.size() < 2) {
            throw new IllegalArgumentException();
        }
    }

    private void groupOrderTables(List<OrderTable> orderTables) {
        for (OrderTable orderTable : orderTables) {
            orderTable.beGrouped(this);
        }
    }

    public void ungroupOrderTables() {
        for (OrderTable orderTable : orderTables) {
            orderTable.beUngrouped();
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables;
    }
}

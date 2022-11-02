package kitchenpos.domain.menu;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.domain.Price;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Price price;

    private Long menuGroupId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        this(name, new Price(price), menuGroupId, menuProducts);
    }

    public Menu(String name, Price price, Long menuGroupId, List<MenuProduct> menuProducts) {
        validatePriceCheaperThanTotal(price, menuProducts);
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    private void validatePriceCheaperThanTotal(Price price, List<MenuProduct> menuProducts) {
        Price sum = menuProducts.stream()
                .map(MenuProduct::getPrice)
                .reduce(Price.ZERO, Price::add);
        if (price.isMoreExpensiveThan(sum)) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 가격의 합계보다 비쌀 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.getAmount();
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
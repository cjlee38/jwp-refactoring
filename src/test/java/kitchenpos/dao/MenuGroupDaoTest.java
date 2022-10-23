package kitchenpos.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import kitchenpos.BeanAssembler;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class MenuGroupDaoTest {

    @Autowired
    private DataSource dataSource;

    private MenuGroupDao menuGroupDao;

    @BeforeEach
    void setUp() {
        menuGroupDao = BeanAssembler.createMenuGroupDao(dataSource);
    }

    @Test
    void save() {
        // given
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("메뉴그룹");

        // when
        MenuGroup savedMenuGroup = menuGroupDao.save(menuGroup);

        // then
        assertThat(savedMenuGroup.getId()).isNotNull();
    }

    @Test
    void findById() {
        // given
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("메뉴그룹");
        MenuGroup savedMenuGroup = menuGroupDao.save(menuGroup);

        // when
        Optional<MenuGroup> foundMenuGroup = menuGroupDao.findById(savedMenuGroup.getId());

        // then
        assertThat(foundMenuGroup).isPresent();
    }

    @Test
    void findAll() {
        // given
        MenuGroup menuGroupA = new MenuGroup();
        menuGroupA.setName("메뉴그룹A");
        menuGroupDao.save(menuGroupA);

        MenuGroup menuGroupB = new MenuGroup();
        menuGroupB.setName("메뉴그룹B");
        menuGroupDao.save(menuGroupB);

        // when
        List<MenuGroup> menuGroups = menuGroupDao.findAll();

        // then
        assertThat(menuGroups).hasSize(4 + 2);
    }

    @Test
    void existsById() {
        // given
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("메뉴그룹");
        MenuGroup savedMenuGroup = menuGroupDao.save(menuGroup);

        // when
        boolean exists = menuGroupDao.existsById(savedMenuGroup.getId());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByIdWithEmptyId() {
        // given
        long invalidId = 999L;

        // when
        boolean exists = menuGroupDao.existsById(invalidId);

        // then
        assertThat(exists).isFalse();
    }
}
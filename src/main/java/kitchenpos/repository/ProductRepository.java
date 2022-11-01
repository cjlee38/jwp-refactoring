package kitchenpos.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.domain.Product;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, Long>, ProductEntityRepository {
    Product save(Product entity);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findAllByIdIn(List<Long> collect);
}

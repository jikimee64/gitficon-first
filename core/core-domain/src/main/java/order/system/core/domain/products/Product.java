package order.system.core.domain.products;

import java.util.Objects;

public class Product {

    private Long productId;

    private Long userId;

    private String name;

    public Product(Long productId, Long userId, String name) {
        this.productId = productId;
        this.userId = userId;
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}

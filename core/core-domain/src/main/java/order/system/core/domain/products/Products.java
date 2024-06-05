package order.system.core.domain.products;

import order.system.core.domain.user.LoginUser;
import order.system.core.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> addProducts(LoginUser loginUser, AddProduct addProduct) {
        products.add(new Product(loginUser.getId(), addProduct.productId(), addProduct.name()));
        return products;
    }

    public Product purchaseProduct(LoginUser loginUser, Long productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다."));
    }

    public List<Product> selectProducts(User user) {
        return products;
    }

    public Product selectProduct(User user, long productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다."));
    }
}

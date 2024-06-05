package order.system.core.domain;

import order.system.core.domain.user.AnonymousUser;
import order.system.core.domain.user.LoginUser;
import order.system.core.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProductsTest {

    private Products products;
    private long userId;

    @BeforeEach
    void setUp() {
        products = new Products(new ArrayList<>());
        userId = 1L;
    }

    @Test
    void 회원은_제품_등록을_할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");

        assertThat(products.addProducts(loginUser, addProduct)).hasSize(1)
                .extracting("userId", "name")
                .containsExactly(
                        tuple(1L, "제품")
                );
    }

    @Test
    void 회원은_제품_구매를_할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");

        products.addProducts(loginUser, addProduct);

        Product purchaseProduct = products.purchaseProduct(loginUser, addProduct.productId());
        assertThat(addProduct.productId()).isEqualTo(purchaseProduct.getProductId());
    }

    @Test
    void 회원은_제품_구매시_해당하는_제품이_없는경우_구매할_수_없다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");

        assertThatThrownBy(() -> {
            products.purchaseProduct(loginUser, addProduct.productId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
    }

    @Test
    void 회원은_제품을_조회할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");

        products.addProducts(loginUser, addProduct);

        assertThat(products.selectProducts(loginUser)).hasSize(1)
                .extracting("userId", "name")
                .containsExactly(
                        tuple(1L, "제품")
                );
    }

    @Test
    void 회원은_제품을_상세_조회할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");

        products.addProducts(loginUser, addProduct);

        assertThat(products.selectProduct(loginUser, addProduct.productId()))
                .extracting("userId", "name")
                .containsExactly(1L, "제품");
    }

    @Test
    void 회원은_없는_제품인_경우_상세_조회할_수_없다() {
        LoginUser loginUser = new LoginUser(userId);

        assertThatThrownBy(() -> {
            products.selectProduct(loginUser, 1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
    }

    @Test
    void 비회원은_제품을_조회할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");
        products.addProducts(loginUser, addProduct);

        User anonymousUser = new AnonymousUser();

        assertThat(products.selectProducts(anonymousUser)).hasSize(1)
                .extracting("userId", "name")
                .containsExactly(
                        tuple(1L, "제품")
                );
    }

    @Test
    void 비회원은_제품을_상세_조회할_수_있다() {
        LoginUser loginUser = new LoginUser(userId);
        AddProduct addProduct = new AddProduct(1L, "제품");
        products.addProducts(loginUser, addProduct);

        User anonymousUser = new AnonymousUser();

        assertThat(products.selectProduct(anonymousUser, addProduct.productId()))
                .extracting("userId", "name")
                .containsExactly(1L, "제품");
    }

    @Test
    void 비회원은_없는_제품인_경우_상세_조회할_수_없다() {
        LoginUser loginUser = new LoginUser(userId);

        User anonymousUser = new AnonymousUser();

        assertThatThrownBy(() -> {
            products.selectProduct(anonymousUser, 1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
    }

}

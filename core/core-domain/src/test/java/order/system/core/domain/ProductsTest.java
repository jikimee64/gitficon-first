package order.system.core.domain;

import order.system.core.domain.user.AnonymousUser;
import order.system.core.domain.user.LoginUser;
import order.system.core.domain.user.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProductsTest {

    private Products products;
    private LoginUser loginUser;
    private User anonymousUser;

    @BeforeEach
    void setUp() {
        products = new Products(new ArrayList<>());
        loginUser = new LoginUser(1L);
        anonymousUser = new AnonymousUser();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원은 {

        @Test
        void 제품을_등록_할_수_있다() {
            AddProduct addProduct = new AddProduct(1L, "제품");

            assertThat(products.addProducts(loginUser, addProduct)).hasSize(1)
                    .extracting("userId", "name")
                    .containsExactly(
                            tuple(1L, "제품")
                    );
        }

        @Test
        void 제품을_구매_할_수_있다() {
            AddProduct addProduct = new AddProduct(1L, "제품");

            products.addProducts(loginUser, addProduct);

            Product purchaseProduct = products.purchaseProduct(loginUser, addProduct.productId());
            assertThat(addProduct.productId()).isEqualTo(purchaseProduct.getProductId());
        }

        @Test
        void 제품_구매시_해당하는_제품이_없는경우_구매할_수_없다() {
            AddProduct addProduct = new AddProduct(1L, "제품");

            assertThatThrownBy(() -> {
                products.purchaseProduct(loginUser, addProduct.productId());
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
        }

        @Test
        void 제품을_조회할_수_있다() {
            AddProduct addProduct = new AddProduct(1L, "제품");

            products.addProducts(loginUser, addProduct);

            assertThat(products.selectProducts(loginUser)).hasSize(1)
                    .extracting("userId", "name")
                    .containsExactly(
                            tuple(1L, "제품")
                    );
        }

        @Test
        void 제품을_상세_조회할_수_있다() {
            AddProduct addProduct = new AddProduct(1L, "제품");

            products.addProducts(loginUser, addProduct);

            assertThat(products.selectProduct(loginUser, addProduct.productId()))
                    .extracting("userId", "name")
                    .containsExactly(1L, "제품");
        }

        @Test
        void 없는_제품인_경우_상세_조회할_수_없다() {
            assertThatThrownBy(() -> {
                products.selectProduct(loginUser, 1L);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
        }

    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 비회원은 {

        @Test
        void 제품을_조회할_수_있다() {
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
        void 제품을_상세_조회할_수_있다() {
            AddProduct addProduct = new AddProduct(1L, "제품");
            products.addProducts(loginUser, addProduct);

            assertThat(products.selectProduct(anonymousUser, addProduct.productId()))
                    .extracting("userId", "name")
                    .containsExactly(1L, "제품");
        }

        @Test
        void 없는_제품인_경우_상세_조회할_수_없다() {
            assertThatThrownBy(() -> {
                products.selectProduct(anonymousUser, 1L);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 제품이 존재하지 않아 제품을 구매할 수 없습니다.");
        }
    }

}

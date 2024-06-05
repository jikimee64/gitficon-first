package order.system.core.domain.products;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AddProductTest {

    @Test
    void AddProduct_생성_유효성_테스트(){
        assertThatThrownBy(() -> {
            new AddProduct(1L, null);
        }).isInstanceOf(ConstraintViolationException.class);
    }

}
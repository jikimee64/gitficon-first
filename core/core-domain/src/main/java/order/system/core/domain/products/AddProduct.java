package order.system.core.domain.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import order.system.core.domain.common.SelfValidating;

public record AddProduct(
        @NotNull
        Long productId,
        @NotBlank
        String name
){
    private static final SelfValidating<AddProduct> validator = new SelfValidating<>() {};

    public AddProduct {
        validator.validateSelf(this);
    }
}

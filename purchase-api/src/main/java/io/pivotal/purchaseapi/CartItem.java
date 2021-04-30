package io.pivotal.purchaseapi;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartItem {
    private UUID id;
    private Product product;
    private Integer quantity;
}

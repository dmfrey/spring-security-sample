package io.pivotal.purchaseapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private UUID id;
    private UUID catalogId;
    private String name;
    private String price;
    private String description;
    private String imageSrc;
    private String imageAlt;
}

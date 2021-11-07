package org.springframework.demo.geekshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "catalogitems")
public class CatalogItem {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private BigDecimal displayPrice;
    private String pictureFileName;
    private String pictureUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_type_id")
    private CatalogType catalogType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_brand_id")
    private CatalogBrand catalogBrand;

    // Quantity in stock
    private Integer availableStock;
    // Available stock at which a reorder of the item should be made to the supplier
    private Integer restockThreshold;
    // Maximum number of units that can be in-stock at any time (due to physical/logistical constraints in warehouses)
    private Integer maxStockThreshold;
}

package org.springframework.demo.geekshop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.demo.geekshop.controller.rest.RestView;

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
    @JsonView(RestView.NormalUser.class)
    private Long id;

    @JsonView(RestView.NormalUser.class)
    private String name;

    @JsonView(RestView.NormalUser.class)
    private String description;

    @JsonView(RestView.NormalUser.class)
    private BigDecimal displayPrice;

    @JsonView(RestView.NormalUser.class)
    private String pictureFileName;

    @JsonView(RestView.NormalUser.class)
    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_type_id")
    private CatalogType catalogType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_brand_id")
    private CatalogBrand catalogBrand;

    // Quantity in stock
    @JsonView(RestView.Admin.class)
    private Integer availableStock;
    // Available stock at which a reorder of the item should be made to the supplier
    @JsonView(RestView.Admin.class)
    private Integer restockThreshold;
    // Maximum number of units that can be in-stock at any time (due to physical/logistical constraints in warehouses)
    @JsonView(RestView.Admin.class)
    private Integer maxStockThreshold;
}

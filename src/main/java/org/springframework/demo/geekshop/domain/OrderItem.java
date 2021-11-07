package org.springframework.demo.geekshop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "catalogitem_id")
    private CatalogItem catalogItem;
    private BigDecimal soldPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

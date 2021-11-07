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
@Table(name = "basketitems")
public class BasketItem {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "catalogitem_id")
    private CatalogItem catalogItem;
    private BigDecimal sellingPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "basketitem_id")
    private CustomerBasket customerBasket;

    public void increaseQuantity() {
        quantity += 1;
    }

    public void decreaseQuantity() {
        if (quantity == 0) {
            throw new UnsupportedOperationException("The operation can not be performed as the quantity is 0 already");
        }
        quantity -= 1;
    }
}
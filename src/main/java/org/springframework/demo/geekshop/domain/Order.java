package org.springframework.demo.geekshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderitem_id")
    private Set<OrderItem> items;

    private String status;
    private LocalDate orderedDate;
    private BigDecimal totalSum;
    private BigDecimal shippingCost;
    private String paymentMethod;

    // shipping details
    private String receiversFirstName;
    private String receiversLastName;
    private String receiversContactNumber;
    private String receiversEmail;
    private String city;
    private String street;
    private String country;
    private String postcode;
}

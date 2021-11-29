package org.springframework.demo.geekshop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalogbrands")
public class CatalogBrand {
    @Id
    @GeneratedValue
    private Long id;

    private String brand;

    public CatalogBrand(String brand) {
        this.id = null;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return brand;
    }
}

package org.springframework.demo.geekshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

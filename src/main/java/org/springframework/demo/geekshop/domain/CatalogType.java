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
@Table(name = "catalogtypes")
public class CatalogType {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    public CatalogType(String type) {
        this.id = null;
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}

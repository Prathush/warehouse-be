package com.warehouse.jpa.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Product Entity class for handling box transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "NAME", unique = true)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "PRODUCT_BOX",
            joinColumns = {
                    @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "BOX_ID", nullable = false, updatable = false)
            }
    )
    Set<BoxEntity> boxes = new HashSet<>();

    public ProductEntity() {

    }
}

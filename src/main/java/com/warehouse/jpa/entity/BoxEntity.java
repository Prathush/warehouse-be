package com.warehouse.jpa.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import com.sun.istack.NotNull;
import lombok.*;

/**
 * Box Entity class for handling box transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOX")
public class BoxEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "BOX_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "NAME", unique = true)
    @NotNull
    private String name;

    @Column(name = "CAPACITY")
    @NotNull
    private int capacity;

    @Column(name = "AVAILABLE_CAPACITY")
    @NotNull
    private int availableCapacity;

    @ManyToMany(mappedBy = "boxes", cascade = {CascadeType.ALL})
    private Set<ProductEntity> productEntities = new HashSet<>();
}

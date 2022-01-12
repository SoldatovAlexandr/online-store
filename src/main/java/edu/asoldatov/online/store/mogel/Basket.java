package edu.asoldatov.online.store.mogel;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "basket")
public class Basket {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "basket")
    private List<ProductItem> products;

    public boolean add(ProductItem product) {
        return products.add(product);
    }

    public boolean remove(ProductItem product) {
        return products.remove(product);
    }
}

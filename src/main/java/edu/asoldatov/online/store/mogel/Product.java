package edu.asoldatov.online.store.mogel;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /**
     *  Изображение;
     * https://yandex.ru/video/preview/?filmId=7451505946330528064&from=tabbar&parent-reqid=1639414109304080-6730104494224415987-sas3-0999-700-sas-l7-balancer-8080-BAL-1854&text=spring+boot+MultipartFile
     */

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "age_limit", nullable = false)
    private Integer ageLimit;

    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @Column(name = "year_of_publication", nullable = false)
    private Integer yearOfPublication;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}

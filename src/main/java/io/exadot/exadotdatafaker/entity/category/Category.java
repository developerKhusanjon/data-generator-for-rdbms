package io.exadot.exadotdatafaker.entity.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;
    private String description;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL})
    private List<CategoryType> categoryTypes;
}

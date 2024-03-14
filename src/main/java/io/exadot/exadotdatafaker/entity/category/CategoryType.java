package io.exadot.exadotdatafaker.entity.category;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;
    private String description;
    private Boolean hasParams;
    private Byte paramsMaxCount;

    private String sample1;
    private String sample2;
    private String sample3;

    @ManyToOne
    private Category category;
}

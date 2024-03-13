package io.exadot.exadotdatafaker.entity.generator;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Boolean hasParams;
    private Byte paramsMaxCount;

    @ManyToOne
    private DataType dataType;
}

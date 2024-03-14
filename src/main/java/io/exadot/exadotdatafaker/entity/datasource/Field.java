package io.exadot.exadotdatafaker.entity.datasource;

import io.exadot.exadotdatafaker.service.dto.enums.FieldStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fieldName;
    private String generateBaseType;
    private String generateValue;
    private FieldStatus fieldStatus;

    @ManyToOne
    private TableEntity table;
}

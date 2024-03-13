package io.exadot.exadotdatafaker.entity;

import io.exadot.exadotdatafaker.service.dto.enums.FilterParamStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FilterParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valueType;
    private String value;

    @Enumerated(EnumType.STRING)
    private FilterParamStatus filterParamStatus;

    @ManyToOne
    private Field field;
}

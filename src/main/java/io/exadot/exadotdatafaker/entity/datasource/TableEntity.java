package io.exadot.exadotdatafaker.entity.datasource;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String generatedKey;

    private String tableName;

    private String schemaName;

    private Long dataSourceId;
}

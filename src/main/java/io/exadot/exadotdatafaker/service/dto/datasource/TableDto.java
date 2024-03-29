package io.exadot.exadotdatafaker.service.dto.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TableDto {
    private Long id;
    private Long dataSourceId;
    private String generatedKey;
    private String tableName;
    private String schemaName;
    private List<FieldDto> fields;
}

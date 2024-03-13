package io.exadot.exadotdatafaker.service.dto.db;

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
    private List<FieldDto> fields;
}

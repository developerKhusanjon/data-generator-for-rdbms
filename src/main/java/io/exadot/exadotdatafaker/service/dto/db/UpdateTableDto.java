package io.exadot.exadotdatafaker.service.dto.db;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTableDto {
    @Positive
    private Long dataSourceId;
    @Positive
    private Long id;
    @NotBlank
    private String generatedKey;
    @NotBlank
    private String tableName;
}

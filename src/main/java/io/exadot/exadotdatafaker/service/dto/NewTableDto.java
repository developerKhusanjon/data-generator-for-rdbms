package io.exadot.exadotdatafaker.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewTableDto {
    @Positive
    private Long dataSourceId;
    @NotBlank
    private String tableName;
    @NotBlank
    private String generatedKey;
}

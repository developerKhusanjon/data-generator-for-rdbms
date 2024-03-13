package io.exadot.exadotdatafaker.service.dto.db;

import io.exadot.exadotdatafaker.service.dto.enums.FieldStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldDto {
    private Long id;
    @NotBlank
    private String fieldName;
    @NotBlank
    private String generateBaseType;
    @NotBlank
    private String generateValue;
    @NotEmpty
    private FieldStatus fieldStatus;
    private List<FilterParamsDto> filterParams;
}
package io.exadot.exadotdatafaker.service.dto.db;

import io.exadot.exadotdatafaker.service.dto.enums.FilterParamStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterParamsDto {
    private Long id;
    @NotBlank
    private String valueType;
    @NotBlank
    private String value;
    @NotNull
    private FilterParamStatus filterParamStatus;
}
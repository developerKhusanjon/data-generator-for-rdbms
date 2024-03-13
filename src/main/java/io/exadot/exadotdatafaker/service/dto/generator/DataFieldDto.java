package io.exadot.exadotdatafaker.service.dto.generator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataFieldDto {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Boolean hasParams;
    @PositiveOrZero
    private Byte paramsMaxCount;
}

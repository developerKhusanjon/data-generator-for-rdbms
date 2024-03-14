package io.exadot.exadotdatafaker.service.dto.category;

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
public class CategoryTypeDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String value;
    private String description;
    @NotNull
    private Boolean hasParams;
    @PositiveOrZero
    private Byte paramsMaxCount;

    private String sample1;
    private String sample2;
    private String sample3;
}

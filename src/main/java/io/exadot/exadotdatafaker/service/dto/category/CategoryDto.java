package io.exadot.exadotdatafaker.service.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String value;
    private String description;

    @NotEmpty
    private List<CategoryTypeDto> categoryTypes;
}

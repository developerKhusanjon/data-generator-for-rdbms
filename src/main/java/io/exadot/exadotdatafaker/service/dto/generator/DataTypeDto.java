package io.exadot.exadotdatafaker.service.dto.generator;

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
public class DataTypeDto {
    private Long id;
    @NotBlank
    private String name;
    private String description;

    @NotEmpty
    private List<DataFieldDto> fields;
}

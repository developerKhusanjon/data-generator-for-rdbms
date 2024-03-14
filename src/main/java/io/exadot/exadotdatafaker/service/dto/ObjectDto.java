package io.exadot.exadotdatafaker.service.dto;

import io.exadot.exadotdatafaker.service.dto.datasource.FieldDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectDto {
    @NotEmpty
    private List<FieldDto> fields;
    @Positive
    private Long count;
}

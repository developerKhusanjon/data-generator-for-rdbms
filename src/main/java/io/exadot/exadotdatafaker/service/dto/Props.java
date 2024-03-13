package io.exadot.exadotdatafaker.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Props {
    @NotNull
    private DataSourceDto dataSource;
    @NotNull
    private TableDto table;
    @Positive
    private Integer count;
}
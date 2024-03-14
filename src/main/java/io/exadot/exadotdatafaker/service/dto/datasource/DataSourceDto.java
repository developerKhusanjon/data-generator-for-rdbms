package io.exadot.exadotdatafaker.service.dto.datasource;


import io.exadot.exadotdatafaker.entity.enums.DatabaseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataSourceDto {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String url;
    @NotBlank
    private String username;
    @NotNull
    private String password;

    private String driver;

    @NotNull
    private DatabaseType databaseType;
}
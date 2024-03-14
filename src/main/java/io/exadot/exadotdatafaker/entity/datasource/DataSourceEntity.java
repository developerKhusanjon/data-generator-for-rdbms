package io.exadot.exadotdatafaker.entity.datasource;

import io.exadot.exadotdatafaker.entity.enums.DatabaseType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DataSourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "driver")
    private String driver;

    @Enumerated(EnumType.STRING)
    private DatabaseType databaseType;
}

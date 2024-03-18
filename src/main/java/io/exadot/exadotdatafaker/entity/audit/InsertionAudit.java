package io.exadot.exadotdatafaker.entity.audit;

import io.exadot.exadotdatafaker.entity.enums.InsertionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InsertionAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataSourceName;
    private String tableName;

    private Long dataSourceId;
    private Long tableId;

    private Long insertCount;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long duration;

    private InsertionStatus status;
    private String failReason;
}

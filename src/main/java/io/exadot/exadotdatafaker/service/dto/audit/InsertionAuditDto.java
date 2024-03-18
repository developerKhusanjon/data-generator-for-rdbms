package io.exadot.exadotdatafaker.service.dto.audit;

import io.exadot.exadotdatafaker.entity.enums.InsertionStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InsertionAuditDto {
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

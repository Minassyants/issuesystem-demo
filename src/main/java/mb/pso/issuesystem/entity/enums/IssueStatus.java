package mb.pso.issuesystem.entity.enums;

import com.fasterxml.jackson.databind.annotation.EnumNaming;

import jakarta.persistence.EnumType;


public enum IssueStatus {
    NEW,
    INPROGRESS,
    PENDINGRESULT,
    CLOSED
}

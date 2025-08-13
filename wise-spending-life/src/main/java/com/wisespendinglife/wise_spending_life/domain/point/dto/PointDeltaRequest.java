package com.wisespendinglife.wise_spending_life.domain.point.dto;

import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;

public interface PointDeltaRequest {
    Long getDelta();
    SourceKind getSourceKind();
}

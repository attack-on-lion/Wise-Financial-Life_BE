package com.wisespendinglife.wise_spending_life.domain.user.service;

import com.wisespendinglife.wise_spending_life.domain.user.entity.User;

public interface UserReader {
    User getEntity(Long id);
}

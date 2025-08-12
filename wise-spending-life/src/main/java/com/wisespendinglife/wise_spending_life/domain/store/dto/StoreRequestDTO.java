package com.wisespendinglife.wise_spending_life.domain.store.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StoreRequestDTO {
    @NotBlank(message = "{INVALID_STORE_NAME}")
    private String storeName;

    @NotNull(message = "{INVALID_CATEGORY}")
    private StoreCategory category;
}

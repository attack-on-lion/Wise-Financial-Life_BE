package com.wisespendinglife.wise_spending_life.domain.store.dto;

import jakarta.validation.constraints.*;
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
    @Size(max = 100)
    private String storeName;

    @NotNull(message = "{INVALID_CATEGORY}")
    private Long categoryId;

    @NotBlank(message = "{INVALID_STORE_IMAGE_URL}")
    @Size(max = 255)
    private String logoUrl;
}

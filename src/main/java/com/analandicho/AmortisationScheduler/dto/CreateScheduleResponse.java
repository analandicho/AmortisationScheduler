package com.analandicho.AmortisationScheduler.dto;

import java.util.UUID;

public class CreateScheduleResponse {

    private UUID assetId;
    private String statusMessage;

    public CreateScheduleResponse(UUID assetId) {
        this.assetId = assetId;
        this.statusMessage = "Schedule creation is successful";

    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}

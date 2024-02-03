package com.analandicho.AmortisationScheduler.dto;

public class CreateScheduleResponse {

    private Long assetId;
    private String statusMessage;

    public CreateScheduleResponse(Long assetId) {
        this.assetId = assetId;
        this.statusMessage = "Schedule creation is successful";

    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}

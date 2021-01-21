package com.employee.loader.model;

public class UploadResponse {

    private String fileName;
    private String fileId;
    private String status;

    public UploadResponse(String fileName, String fileId, String status) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.status = status != null ? status : (fileId != null ? "" : "Invalid action");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadResponse() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

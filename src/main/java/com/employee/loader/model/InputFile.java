package com.employee.loader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class InputFile {


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Id
    @Column
    private String fileId;
    @Column
    private String fileName;
    @Column
    private Status status;

    public InputFile(String fileId, String fileName, Status status) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.status = status;
    }

    public InputFile() {
    }
}


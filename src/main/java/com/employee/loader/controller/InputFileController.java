package com.employee.loader.controller;

import com.employee.loader.model.InputFile;
import com.employee.loader.model.UploadResponse;
import com.employee.loader.service.InputFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@RestController
public class InputFileController {

    @Autowired
    private InputFileService inputFileService;

    @PostMapping("/api/employee")
    public UploadResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("action") String action) {
        String fileId = "FILE_" + new Date().getTime();
        String fileName = inputFileService.save(file, fileId);

        if (!"upload".equals(action)) {
            return new UploadResponse(fileName, null, null);
        }

        String statusUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/status/")
                .path(fileId)
                .toUriString();

        return new UploadResponse(fileName, fileId, statusUri);
    }

    @GetMapping("/api/status/{fileId}")
    public InputFile getStatus(@PathVariable String fileId) {
        return inputFileService.getFile(fileId);
    }

}

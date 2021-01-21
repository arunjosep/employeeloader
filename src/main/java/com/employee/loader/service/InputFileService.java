package com.employee.loader.service;

import com.employee.loader.exception.UnparsableDataException;
import com.employee.loader.model.InputFile;
import com.employee.loader.model.Status;
import com.employee.loader.repository.InputFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InputFileService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InputFileRepository inputFileRepository;

    public InputFileService() {
    }

    public String save(MultipartFile file, String fileId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //Create a new line in InputFiles with processing status
        InputFile inputFile = new InputFile(fileId, fileName, Status.PROCESSING);
        inputFileRepository.save(inputFile);

        //Set to false if at least one line fails
        AtomicReference<Boolean> status = new AtomicReference<>(true);

        // Start a thread to process the input file
        Executors.newFixedThreadPool(1).submit(() -> {
            try {

                Arrays.stream(new String(file.getBytes()).split("\\r?\\n")).forEach(line -> {
                    try {
                        // Load each employee line
                        loadEmployee(line, fileId);
                    } catch (UnparsableDataException e) {
                        status.set(false);
                    }
                });

            } catch (IOException ex) {
                employeeService.deleteBySource(fileId);
                inputFile.setStatus(Status.FAILED);
                inputFileRepository.save(inputFile);
            } finally {
                if (status.get()) {
                    //All lines successful, clear the source field
                    inputFile.setStatus(Status.COMPLETE);
                    inputFileRepository.save(inputFile);
                    employeeService.clearSource(fileId);

                } else {
                    // At least one line failed. Delete all lines from this file
                    employeeService.deleteBySource(fileId);
                    inputFile.setStatus(Status.FAILED);
                    inputFileRepository.save(inputFile);
                }
                return null;
            }
        });

        // Respond with generated file code
        return fileName;
    }

    private void loadEmployee(String employeeData, String fileId) throws UnparsableDataException {
        //System.out.println(employeeData);
        employeeService.addEmployee(employeeData, fileId);
    }

    public InputFile getFile(String fileId) {
        return (InputFile) inputFileRepository.findById(fileId).get();

    }
}

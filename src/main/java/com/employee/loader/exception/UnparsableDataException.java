package com.employee.loader.exception;

import java.util.InputMismatchException;

public class UnparsableDataException extends InputMismatchException {
    public UnparsableDataException(String nameAndAge) {
    }
}

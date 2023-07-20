package com.gafarov.bastion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.model.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BaseController {
    @ExceptionHandler(ConflictDataException.class)
    void handleConflictData(HttpServletResponse response, Exception exception) throws IOException {
        sendResponse(response, HttpServletResponse.SC_CONFLICT, exception.getMessage());
    }
    void sendResponse(HttpServletResponse response, int status, String errorMsg) throws IOException {
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            bw.write(mapper.writeValueAsString(new ErrorResponse(errorMsg)));
        }
    }



}

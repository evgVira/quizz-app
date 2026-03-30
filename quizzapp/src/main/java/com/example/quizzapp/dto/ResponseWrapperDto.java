package com.example.quizzapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapperDto<T> {

    private T result;
    private Boolean status;
    private String errorMessage;

    public ResponseWrapperDto<T> buildSuccessResponse() {
        this.result = null;
        this.errorMessage = null;
        this.status = true;
        return this;
    }

    public ResponseWrapperDto<T> buildSuccessResponse(T result) {
        this.result = result;
        this.errorMessage = null;
        this.status = true;
        return this;
    }

    public ResponseWrapperDto<T> buildErrorResponse(String errorMessage) {
        this.result = null;
        this.errorMessage = errorMessage;
        this.status = false;
        return this;
    }

    public ResponseWrapperDto<T> buildErrorResponse(T result, String errorMessage) {
        this.result = null;
        this.errorMessage = errorMessage;
        this.status = false;
        return this;
    }

}

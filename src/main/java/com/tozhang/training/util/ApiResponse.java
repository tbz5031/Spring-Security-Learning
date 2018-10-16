package com.tozhang.training.util;

import org.springframework.http.HttpStatus;

public class ApiResponse {

    private HttpStatus status;
    private String error_code;
    private String message;
    private Object detail;

    public HttpStatus getStatus() {
        return status;
    }

    public String getError_code() {
        return error_code;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetail() {
        return detail;
    }

    // getter and setters
    //Builder
    public static final class ApiResponseBuilder {
        private HttpStatus status;
        private String error_code;
        private String message;
        private Object detail;

        public HttpStatus getStatus() {
            return status;
        }

        public String getError_code() {
            return error_code;
        }

        public String getMessage() {
            return message;
        }

        public Object getDetail() {
            return detail;
        }

        public ApiResponseBuilder() {
        }

        public static ApiResponseBuilder anApiResponse() {
            return new ApiResponseBuilder();
        }

        public ApiResponseBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder withError_code(String error_code) {
            this.error_code = error_code;
            return this;
        }

        public ApiResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder withDetail(Object detail) {
            this.detail = detail;
            return this;
        }

        public ApiResponse build() {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.status = this.status;
            apiResponse.error_code = this.error_code;
            apiResponse.detail = this.detail;
            apiResponse.message = this.message;
            return apiResponse;
        }
    }
}
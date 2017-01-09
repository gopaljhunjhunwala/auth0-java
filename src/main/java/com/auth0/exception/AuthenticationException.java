package com.auth0.exception;

import java.util.Map;

public class AuthenticationException extends Auth0Exception {

    private String error;
    private String description;
    private int statusCode;

    public AuthenticationException(String payload, int statusCode, Throwable cause) {
        super(createMessage(payload, statusCode), cause);
        this.description = payload;
        this.statusCode = statusCode;
    }

    public AuthenticationException(Map<String, Object> values, int statusCode) {
        super(createMessage(obtainExceptionMessage(values), statusCode));
        this.error = (String) (values.containsKey("error") ? values.get("error") : values.get("code"));
        this.description = (String) (values.containsKey("error_description") ? values.get("error_description") : values.get("description"));
        this.statusCode = statusCode;
    }

    private static String obtainExceptionMessage(Map<String, Object> values) {
        if (values.containsKey("error_description")) {
            return (String) values.get("error_description");
        }
        if (values.containsKey("description")) {
            return (String) values.get("description");
        }
        if (values.containsKey("error")) {
            return (String) values.get("error");
        }
        return "Unknown error";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    private static String createMessage(String description, int statusCode) {
        return String.format("Authentication failed with status code %d: %s", statusCode, description);
    }
}

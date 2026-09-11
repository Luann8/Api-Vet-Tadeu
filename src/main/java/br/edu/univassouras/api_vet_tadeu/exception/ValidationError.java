package br.edu.univassouras.api_vet_tadeu.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> fieldErrors = new ArrayList<>();

    public ValidationError() {
        super();
    }

    public ValidationError(Integer status, String error, String message, String path) {
        super(status, error, message, path);
    }

    public List<FieldMessage> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String fieldName, String message) {
        this.fieldErrors.add(new FieldMessage(fieldName, message));
    }

    public static class FieldMessage {
        private String field;
        private String message;

        public FieldMessage() {
        }

        public FieldMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

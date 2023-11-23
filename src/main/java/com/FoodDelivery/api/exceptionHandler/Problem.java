package com.FoodDelivery.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {
    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<FieldError> fieldErrors;

    private Problem(){};

    @Getter
    public static class FieldError {
        private String field;
        private String userMessage;

        private FieldError(){}

        public static class FieldErrorBuilder {
            private FieldError instance = new FieldError();

            public FieldErrorBuilder field (String field) {
                instance.field = field;
                return this;
            }

            public FieldErrorBuilder userMessage (String userMessage) {
                instance.userMessage = userMessage;
                return this;
            }

            public FieldError build (){
                return instance;
            }
        }
    }

    public static class BuilderClass {
        private Problem instance = new Problem();

        public BuilderClass status(Integer status) {
            instance.status = status;
            return this;
        }

        public BuilderClass type (String type) {
            instance.type = type;
            return this;
        }

        public BuilderClass title (String title) {
            instance.title = title;
            return this;
        }

        public BuilderClass detail (String detail) {
            instance.detail = detail;
            return this;
        }

        public BuilderClass userMessage (String userMessage) {
            instance.userMessage = userMessage;
            return this;
        }

        public BuilderClass fieldErrors (List<FieldError> fieldErrors) {
            instance.fieldErrors = fieldErrors;
            return this;
        }

        public Problem build() {
            return instance;
        }
    }
}

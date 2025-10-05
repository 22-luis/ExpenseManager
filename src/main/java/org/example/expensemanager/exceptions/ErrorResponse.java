package org.example.expensemanager.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

@Setter
@Getter
public abstract class ErrorResponse {

    private int status;
    private String message;
    private String details;

    public ErrorResponse(int status, String message, String details, String description){
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public abstract HttpStatusCode getStatusCode();

    public abstract ProblemDetail getBody();
}

package mb.pso.issuesystem.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mb.pso.issuesystem.exceptions.ChatNotFoundException;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.exceptions.IssueNotFoundException;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IssueNotFoundException.class)
    protected ResponseEntity<String> handleIssueNotFoundException(IssueNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalActionException.class)
    protected ResponseEntity<String> handleIllegalActionException(IllegalActionException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ChatNotFoundException.class)
    protected ResponseEntity<String> handleChatNotFoundException(ChatNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getLocalizedMessage());
    }
}

package mb.pso.issuesystem.controller.rest;

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

    @ExceptionHandler({ IssueNotFoundException.class })
    protected ResponseEntity<String> handleIssueNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(499).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler({ IllegalActionException.class })
    protected ResponseEntity<String> handleIllegalActionException(Exception ex, WebRequest request) {
        return ResponseEntity.status(498).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler({ ChatNotFoundException.class })
    protected ResponseEntity<String> handleChatNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(497).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler({ EmployeeNotFoundException.class })
    protected ResponseEntity<String> handleEmployeeNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(496).body(ex.getLocalizedMessage());
    }

}

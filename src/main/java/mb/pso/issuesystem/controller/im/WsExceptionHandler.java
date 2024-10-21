package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import mb.pso.issuesystem.exceptions.ChatNotFoundException;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.exceptions.IssueNotFoundException;

@ControllerAdvice
public class WsExceptionHandler {

    @MessageExceptionHandler({ IllegalActionException.class, ChatNotFoundException.class,
            IssueNotFoundException.class, EmployeeNotFoundException.class })
    @SendToUser("/errors")
    protected String handleIllegalActionException(Exception ex) {
        return ex.getLocalizedMessage();
    }

}

package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import mb.pso.issuesystem.dto.ws.SocketMsg;
import mb.pso.issuesystem.exceptions.ChatNotFoundException;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.exceptions.IssueNotFoundException;
import mb.pso.issuesystem.exceptions.MessageNotFoundException;


@ControllerAdvice
public class WsExceptionHandler {

    @MessageExceptionHandler({
            IllegalActionException.class,
            ChatNotFoundException.class,
            MessageNotFoundException.class,
            IssueNotFoundException.class,
            EmployeeNotFoundException.class
    })
    @SendToUser("topic/errors")
    protected SocketMsg handleBusinessExceptions(Exception ex) {
        return new SocketMsg(SocketMsg.MsgType.ERROR, ex.getMessage());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/topic/errors")
    public SocketMsg handleGeneralExceptions(Exception ex) {
        return new SocketMsg(SocketMsg.MsgType.ERROR, "An unexpected error occurred: " + ex.getMessage());
    }

}

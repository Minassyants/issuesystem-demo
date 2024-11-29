package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import mb.pso.issuesystem.dto.webSocket.SocketMsg;
import mb.pso.issuesystem.exceptions.ChatNotFoundException;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.exceptions.IssueNotFoundException;
import mb.pso.issuesystem.exceptions.MessageNotFoundException;
//[ ] REFACTOR
@ControllerAdvice
public class WsExceptionHandler {

    @MessageExceptionHandler({ IllegalActionException.class, ChatNotFoundException.class,
            MessageNotFoundException.class,
            IssueNotFoundException.class, EmployeeNotFoundException.class })

    @SendToUser("topic/errors")
    protected SocketMsg handleIllegalActionException(Exception ex) {
        return new SocketMsg(SocketMsg.MsgType.ERROR, ex.getLocalizedMessage());
    }

}

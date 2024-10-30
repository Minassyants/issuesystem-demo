package mb.pso.issuesystem.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /*
     * NotificationPolicy (ONLYINAPP,INAPP,BOTH,ONLYMAIL)
     * Type ()
     * isRead bool
     * isSent bool
     * 
     */
}

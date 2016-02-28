package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;

/**
 *
 * Created by howen on 15/12/24.
 */
public class SchedulerActor extends AbstractActor {

    public SchedulerActor() {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Logger.error("消息---> "+orderId);
        }).matchAny(s -> {
            Logger.error("CancelOrderActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

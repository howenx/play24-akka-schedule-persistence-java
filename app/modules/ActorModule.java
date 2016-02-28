package modules;

import actor.DelScheduleActor;
import actor.SchedulerActor;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;

/**
 * Akka Actor Module
 * Created by howen on 15/12/14.
 */
public class ActorModule extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
        bindActor(SchedulerActor.class,"schedulerActor");
        bindActor(DelScheduleActor.class,"delScheduleActor");
    }
}

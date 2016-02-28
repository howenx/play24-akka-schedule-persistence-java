package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import domain.Persist;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import play.api.libs.Codecs;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application extends Controller {

    @Inject
    private ActorSystem system;

    @Inject
    @Named("schedulerActor")
    private ActorRef schedulerActor;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    private LevelFactory levelFactory;


    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    /**
     * 处理系统启动时候去做第一次请求,完成对定时任务的执行
     *
     * @return string
     */
    public Result getFirstApp(String cipher) {
        if (Codecs.md5("hmm-100901".getBytes()).equals(cipher)) {
            List<Persist> persists;
            try {
                persists = levelFactory.iterator();
                if (persists != null && persists.size() > 0) {
                    Logger.info("遍历所有持久化schedule---->\n" + persists);
                    for (Persist p : persists) {
                        Long time = p.getDelay() - (new Date().getTime() - p.getCreateAt().getTime());
                        Logger.info("重启后schedule执行时间---> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime()+time)));
                        if (time > 0) {
                            ActorSelection sel = system.actorSelection(p.getActorPath());
                            Future<ActorRef> fut = sel.resolveOne(TIMEOUT);
                            ActorRef ref = Await.result(fut, TIMEOUT.duration());
                            newScheduler.scheduleOnce(Duration.create(time, TimeUnit.MILLISECONDS), ref, p.getMessage());
                        } else {
                            levelFactory.delete(p.getMessage());
                            system.actorSelection(p.getActorPath()).tell(p.getMessage(), ActorRef.noSender());
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return notFound("error");
            }
            return ok("success");
        } else throw new NullPointerException(cipher);
    }

    public Result hello() {
        newScheduler.scheduleOnce(Duration.create(180, TimeUnit.SECONDS), schedulerActor, 77701093L);
        return ok("hello");
    }
}

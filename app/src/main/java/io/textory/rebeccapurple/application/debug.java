package io.textory.rebeccapurple.application;

import android.os.Handler;

import rebeccapurple.Function;
import rebeccapurple.exception.CancelledScheduleException;
import rebeccapurple.schedule.Time;
import rebeccapurple.schedule.Timeout;

/**
 *
 * @author      sean <hyunsik-park@textory.com>
 * @since       0.0.1
 */

public class debug {
    private static Handler __handler;

    public static <T extends rebeccapurple.scheduler.Task> void scheduler(T schedule, long delay, Function<T, Runnable> factory){
        try {
            __handler.postDelayed(factory.call(schedule), delay);
        } catch (Throwable e) {
            functional.log.e(schedule, e);
        }
    }

    public static void run(){
        __handler = new Handler();

        functional.scheduler.dispatch(new Time(functional.timestamp.after(1000L), functional.scheduler::log));
        functional.scheduler.dispatch(new Timeout(2000L, functional.scheduler::log));
        scheduler(functional.scheduler.dispatch(new Timeout(3000L, functional.scheduler::log)), 1000L, schedule->()->schedule.cancel(new CancelledScheduleException()));
        scheduler(functional.scheduler.dispatch(new Timeout(4000L, functional.scheduler::log)), 2000L, schedule->()->functional.scheduler.reset(schedule));
    }
}

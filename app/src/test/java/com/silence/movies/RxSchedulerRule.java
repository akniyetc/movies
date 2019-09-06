package com.silence.movies;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class RxSchedulerRule implements TestRule {

    private Scheduler immediate = new Scheduler() {

        @Override
        public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
        }
    };

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(handler -> immediate);
                RxJavaPlugins.setInitComputationSchedulerHandler(handler -> immediate);
                RxJavaPlugins.setInitNewThreadSchedulerHandler(handler -> immediate);
                RxJavaPlugins.setInitSingleSchedulerHandler (handler -> immediate);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(handler -> immediate);

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
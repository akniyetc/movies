package com.silence.movies.system;

import io.reactivex.Scheduler;

public interface SchedulersProvider {
    Scheduler io();
    Scheduler ui();
    Scheduler trampoline();
}

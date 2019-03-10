package com.webhw;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Shared {

    static volatile AtomicBoolean professor_ready = new AtomicBoolean(false);
    static volatile AtomicBoolean assistant_ready = new AtomicBoolean(false);

    static volatile AtomicInteger sum_of_all_grades = new AtomicInteger(0);
    static volatile AtomicInteger number_of_grades = new AtomicInteger(0);

}

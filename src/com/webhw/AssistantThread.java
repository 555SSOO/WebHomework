package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AssistantThread implements Callable {

    private AtomicBoolean isAvailable = new AtomicBoolean(true);

    @Override
    public Object call() {
        // Signal that the assistant is ready as soon as he is created
        Shared.assistant_ready.set(true);
        // Keep this thread running
        while (true) {
        }
    }

    // Grade the students work with a random number from 5 to 10
    int gradeWork() {
        return Util.getRandomNumber(5, 10);
    }

    // Return the name of the running assistant thread
    String getThreadName() {
        return Thread.currentThread().getName();
    }

    AtomicBoolean getIsAvailable() {
        return isAvailable;
    }
    void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

}

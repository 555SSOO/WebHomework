package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfessorThread implements Callable {

    private AtomicInteger busy_status = new AtomicInteger(0);

    @Override
    public Object call() {
        //  Signal the professor is ready as soon as he is created
        Shared.professor_ready.set(true);
        // Keep this thread running
        while(true) {
        }
    }

    // Grade the students work with a random number from 5 to 10
    int gradeWork(){
        return Util.getRandomNumber(5,10);
    }

    // Return the name of the professor thread
    String getThreadName(){
        return "P" + Thread.currentThread().getName();
    }

    // Returns false Atomic Boolean if the professor has more then 2 students at a time
    AtomicBoolean getIsAvailable() {
        if(this.busy_status.get() == 2){
            return new AtomicBoolean(false);
        }
        else{
            return new AtomicBoolean(true);
        }
    }

    void incrementBusyStatus() {
        this.busy_status.addAndGet(1);
    }
    void decrementBusyStatus(){
        this.busy_status.decrementAndGet();
    }

}

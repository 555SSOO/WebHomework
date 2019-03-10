package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfessorThread implements Callable {

    public AtomicInteger busy_status = new AtomicInteger(0);

    public CyclicBarrier barrier = new CyclicBarrier(2);

    ProfessorThread(){
    }

    @Override
    public Object call() throws Exception {

        Shared.professor_ready.set(true);

        while(true) {
        }
    }

    public int gradeWork(){
        return Util.getRandomNumber(5,10);
    }

    public String getThreadName(){
        return "P" + Thread.currentThread().getName();
    }

    public AtomicBoolean getIsAvailable() {
        if(this.busy_status.get() == 2){
            return new AtomicBoolean(false);
        }
        else{
            return new AtomicBoolean(true);
        }
    }

    public void incrementBusyStatus() {
        this.busy_status.addAndGet(1);
    }

    public void decrementBusyStatus(){
        this.busy_status.decrementAndGet();
    }

}

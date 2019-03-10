package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AssistantThread implements Callable {

    private AtomicBoolean isAvailable = new AtomicBoolean(true);

    AssistantThread(){
    }

    @Override
    public Object call() throws Exception {

        Shared.assistant_ready.set(true);

        while(true) {
        }
    }

    public int gradeWork(){
        return Util.getRandomNumber(5,10);
    }

    public String getThreadName(){
        return "A" + Thread.currentThread().getName();
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

}

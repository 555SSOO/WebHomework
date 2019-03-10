package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AssistantThread implements Callable {

    private AtomicBoolean isAvailable = new AtomicBoolean(true);

    AssistantThread(){
    }

    @Override
    public Object call() throws Exception {

        Shared.assistant_ready = true;

        while(true) {
        //System.out.println("AS THREAD");
        }
        //return null;
    }

    public int gradeWork(){
        return Util.getRandomNumber(5,10);
    }

    public String getThreadName(){
        return "Asistent1";
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

}

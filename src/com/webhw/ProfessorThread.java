package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfessorThread implements Callable {

    public AtomicBoolean isAvailable = new AtomicBoolean(true);

    ProfessorThread(){
    }

    @Override
    public Object call() throws Exception {

        Shared.professor_ready = true;

        CyclicBarrier barrier = new CyclicBarrier(2);

        while(true) {
            //System.out.println("PROFESOR THREAD");
        }
        //return null;
    }


    public int gradeWork(){
        return Util.getRandomNumber(5,10);
    }

    public String getThreadName(){
        return "Profesor1";
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

}

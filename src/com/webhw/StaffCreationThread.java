package com.webhw;

import java.util.concurrent.*;

public class StaffCreationThread implements Runnable{

    private ProfessorThread professor_thread;
    private AssistantThread assistant_thread;

    StaffCreationThread(ProfessorThread professor_thread, AssistantThread assistant_thread){
        this.professor_thread = professor_thread;
        this.assistant_thread = assistant_thread;
    }

    @Override
    public void run() {
        ExecutorService staff_executor = Executors.newFixedThreadPool(2);
        try {
            staff_executor.submit(professor_thread).get(5, TimeUnit.SECONDS);
            staff_executor.submit(assistant_thread).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("5 seconds up");
        }
    }
}
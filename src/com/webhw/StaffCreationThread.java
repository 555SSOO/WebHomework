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
            // Start the execution of both the professor and assistant threads with a timeout of 5 seconds
            staff_executor.submit(professor_thread).get(5, TimeUnit.SECONDS);
            staff_executor.submit(assistant_thread).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // We get the TimeoutException after 5 seconds are up and close the running threads
            staff_executor.shutdownNow();
        }
        // After we finish execution, we print out the sum of all grades and the average grade
        System.out.println("Total grade sum = " + Shared.sum_of_all_grades +"\nTotal number of students graded = " + Shared.number_of_grades
        + "\nGrade average = " + (double) Shared.sum_of_all_grades.get() / Shared.number_of_grades.get());

        // Finish system execution
        System.exit(0);
    }
}

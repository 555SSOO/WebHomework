package com.webhw;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        // Get the number of students from the command line
        int number_of_students = Integer.valueOf(args[0]);

        // Thread handles
        ProfessorThread professor_thread = new ProfessorThread();
        AssistantThread assistant_thread = new AssistantThread();

        // Creating a new thread that starts the p/a threads because of blockers
        Runnable staff_creation_thread = new StaffCreationThread(professor_thread, assistant_thread);
        new Thread(staff_creation_thread).start();

        while(!Shared.assistant_ready && !Shared.professor_ready){
        }
        // Start the Student thread pool after both assistant and professor are ready
        ScheduledExecutorService student_executor = Executors.newScheduledThreadPool(number_of_students);
        for(int i = 0; i < number_of_students; i++) {
            int start_time = Util.getRandomNumber(0,1000);
            student_executor.schedule(new StudentThread(professor_thread, assistant_thread, start_time), start_time, TimeUnit.MILLISECONDS);
        }

    }
}

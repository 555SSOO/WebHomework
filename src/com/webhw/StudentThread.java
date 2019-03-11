package com.webhw;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class StudentThread implements Callable {

    private int grade;
    private int start_time;
    private ProfessorThread professor_thread;
    private AssistantThread assistant_thread;
    private CyclicBarrier barrier;

    StudentThread(ProfessorThread professor_thread, AssistantThread assistant_thread, int start_time, CyclicBarrier barrier) {
        this.professor_thread = professor_thread;
        this.assistant_thread = assistant_thread;
        this.start_time = start_time;
        this.barrier = barrier;
    }

    @Override
    public Object call() throws Exception {

        // Only let one thread check and decrease availability at a time
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        // Get an int that represents who is available
        int assistant_or_professor = schedule();
        semaphore.release();

        // If the professor is available, we need to wait until there are 2 students in the queue
        if(assistant_or_professor == 2){
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        // Simulate the review time with a random timeout
        int review_time = Util.getRandomNumber(500, 1000);
        waitForReview(review_time);

        // If we are being graded by the assistant
        if(assistant_or_professor == 1) {
            // Get the grade from the assistant thread
            this.grade = assistant_thread.gradeWork();
            // Print out the results
            System.out.println("Thread: " + Thread.currentThread().getName() + " Arrival: " +
                    +start_time + "ms Prof: " + assistant_thread.getThreadName() +
                    " TTC: " + review_time + "ms:domaci>:<vreme\n" +
                    "pocetka odbrane> ms Score: " + this.grade);
            // Signal that assistant is available again
            assistant_thread.setIsAvailable(true);
        }
        // If we are being graded by the professor
        else if (assistant_or_professor == 2){
            // Get the grade from the professor thread
            this.grade = professor_thread.gradeWork();
            // Print out the results
            System.out.println("Thread: " + Thread.currentThread().getName() + " Arrival: " +
                    +start_time + "ms Prof: " + professor_thread.getThreadName() +
                    " TTC: " + review_time + "ms:domaci>:<vreme\n" +
                    "pocetka odbrane> ms Score: " + this.grade);
            // Signal that professor has a spot open
            professor_thread.decrementBusyStatus();
        }
        // Input our grade to the sum of all grades
        inputGrade(this.grade);
        return null;
    }

    // Returns 1 if we scheduled this student for the assistant, and 2 if we scheduled him for the professor
    // This is a critical section
    private int schedule(){
        while (!assistant_thread.getIsAvailable().get() || !professor_thread.getIsAvailable().get()) {
        }
        if(assistant_thread.getIsAvailable().get()){
            assistant_thread.setIsAvailable(false);
            return 1;
        }
        else if(professor_thread.getIsAvailable().get()){
            professor_thread.incrementBusyStatus();
            return 2;
        }
        return 0;
    }

    // This simulates grade review time
    private void waitForReview(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    // This can access the shared resources without a lock because it's just adding to atomic ints
    // If that was not the case, we would need to put this inside a semaphore or syn block
    // This method adds the students grade to the sum of all student grades and increments the number of students graded
    private void inputGrade(int grade) {
        Shared.sum_of_all_grades.addAndGet(grade);
        Shared.number_of_grades.addAndGet(1);
    }
    
}

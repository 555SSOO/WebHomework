package com.webhw;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class StudentThread implements Callable {

    public int grade;
    public int start_time;
    public ProfessorThread professor_thread;
    public AssistantThread assistant_thread;
    public CyclicBarrier barrier;

    StudentThread(ProfessorThread professor_thread, AssistantThread assistant_thread, int start_time, CyclicBarrier barrier) {
        this.professor_thread = professor_thread;
        this.assistant_thread = assistant_thread;
        this.start_time = start_time;
        this.barrier = barrier;
    }

    @Override
    public Object call() throws Exception {
        int assistant_or_professor = schedule();

        if(assistant_or_professor == 2){

        }

        int review_time = Util.getRandomNumber(500, 1000);
        waitForReview(review_time);



        if(assistant_or_professor == 1) {
            this.grade = assistant_thread.gradeWork();
            System.out.println("Thread: " + Thread.currentThread().getName() + " Arrival: " +
                    +start_time + "ms Prof: " + assistant_thread.getThreadName() +
                    " TTC: " + review_time + "ms:domaci>:<vreme\n" +
                    "pocetka odbrane> ms Score: " + this.grade);
            assistant_thread.setIsAvailable(true);
        }
        else if (assistant_or_professor == 2){
            this.grade = professor_thread.gradeWork();
            System.out.println("Thread: " + Thread.currentThread().getName() + " Arrival: " +
                    +start_time + "ms Prof: " + professor_thread.getThreadName() +
                    " TTC: " + review_time + "ms:domaci>:<vreme\n" +
                    "pocetka odbrane> ms Score: " + this.grade);
            professor_thread.decrementBusyStatus();
        }

        inputGrade(this.grade);

        return null;
    }

    // Returns 1 if we scheduled this student for the assistant, and 2 if we scheduled him for the professor
    public synchronized int schedule(){
        while (!assistant_thread.getIsAvailable().get() || !professor_thread.getIsAvailable().get()) {
        }
        if(assistant_thread.getIsAvailable().get()){
            assistant_thread.setIsAvailable(false);
            return 1;
        }
        else if(professor_thread.getIsAvailable().get()){

            System.out.println("pre await");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("passed await");

            professor_thread.incrementBusyStatus();

            return 2;
        }
        return 0;
    }


    public void waitForReview(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void inputGrade(int grade) {
        Shared.sum_of_all_grades.addAndGet(grade);
        Shared.number_of_grades.addAndGet(1);
    }


}

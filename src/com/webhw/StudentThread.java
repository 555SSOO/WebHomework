package com.webhw;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class StudentThread implements Callable {

    public int grade;
    public int start_time;
    public ProfessorThread professor_thread;
    public AssistantThread assistant_thread;


    StudentThread(ProfessorThread professor_thread, AssistantThread assistant_thread, int start_time) {
        this.professor_thread = professor_thread;
        this.assistant_thread = assistant_thread;
        this.start_time = start_time;
    }

    @Override
    public Object call() throws Exception {


        synchronized (this) {
            while (!assistant_thread.getIsAvailable().get()) {
            }
            assistant_thread.setIsAvailable(false);
        }


        int review_time = Util.getRandomNumber(500, 1000);
        waitForReview(review_time);

        this.grade = assistant_thread.gradeWork();

        System.out.println("Thread: " + Thread.currentThread().getName() + " Arrival: " +
                +start_time + "ms Prof: " + assistant_thread.getThreadName() +
                " TTC: " + review_time + "ms:domaci>:<vreme\n" +
                "pocetka odbrane> ms Score: " + this.grade);

        //Syn?
        assistant_thread.setIsAvailable(true);

        inputGrade(this.grade);

        return null;
    }

    public void waitForReview(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void inputGrade(int grade) {
        Shared.sum_of_all_grades += grade;
        Shared.number_of_grades += 1;
    }


}

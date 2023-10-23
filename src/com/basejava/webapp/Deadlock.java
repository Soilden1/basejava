package com.basejava.webapp;

public class Deadlock {

    static final String lock1 = "lock1";
    static final String lock2 = "lock2";
    static final int sleepTime = 1000;
    static int countThread = 1;

    public static void main(String[] args) {
        deadlock(lock1, lock2);
        deadlock(lock2, lock1);
    }

    private static void deadlock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " wait " + lock1);
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep for " + sleepTime + " milliseconds");
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("This command will not execute");
                }
            }
        }, "Thread" + countThread++).start();
    }
}

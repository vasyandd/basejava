package com.urise.webapp;

public class MainConcurrent {

    private static final Object OBJECT1 = new Object();
    private static final Object OBJECT2 = new Object();
    public static void main(String[] args) {
        MainConcurrent mainConcurrent = new MainConcurrent();
        System.out.println(mainConcurrent.getClass());
        final Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("RUN " + this.getName());
            }
        };
//        thread.start();
        MainConcurrent mainConcurrent1 = new MainConcurrent();
        Thread thread1 = new Thread(() -> {
            synchronized (mainConcurrent) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (mainConcurrent1) {

                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            synchronized (mainConcurrent1) {
                synchronized (mainConcurrent) {

                }
            }
        });
        thread2.start();
    }


}

package com.github.yadsendew;

public class ExecutingThread implements Runnable {


    @Override
    public void run() {
        System.out.println("Executing");
        Thread.sleep(1);
    }

}
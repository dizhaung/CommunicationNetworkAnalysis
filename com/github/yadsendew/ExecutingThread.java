package com.github.yadsendew;

public class ExecutingThread extends Thread {

    @Override
    public void run() {
        System.out.print("Processing content");
        while (Thread.interrupted() == false) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                
                this.interrupt();
            }
        }
        System.out.println();
    }
    
}
package com.github.yadsendew;

public class ReadFileThread implements Runnable {

    String file;

    @Override
    public void run() {
        System.out.print("Reading file " + this.file);
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println();
                return;
            }
        }
        
    }

    ReadFileThread(String fileName) {
        file = fileName;
    }

}
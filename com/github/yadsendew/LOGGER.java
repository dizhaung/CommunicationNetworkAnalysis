package com.github.yadsendew;

import java.io.IOException;
import java.util.logging.*;

public class LOGGER {
    static Logger logger;
    public Handler fileHandler;
    Formatter plainText;

    public LOGGER() throws IOException{
        //instance the logger
        logger = Logger.getLogger(LOGGER.class.getName());
        //instance the filehandler
        fileHandler = new FileHandler("logfile.log", true);
        //instance formatter, set formatting, and handler
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
    }
    private static Logger getLogger(){
        if(logger == null){
            try {
                new LOGGER();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
        System.out.println(msg);
    }
}
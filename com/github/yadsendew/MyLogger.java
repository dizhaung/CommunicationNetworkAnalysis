package com.github.yadsendew;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {
	final Logger LOGGER = Logger.getLogger("PublicLogger"); 
	MyLogger(){
		LOGGER.setUseParentHandlers(false);
		Handler fileHandler;
		try {
			fileHandler = new FileHandler("logfile.log", true); 
			LOGGER. addHandler ( fileHandler ); 
			fileHandler.setFormatter(new SimpleFormatter()); 
			fileHandler.setLevel(Level.ALL); 
			throw new IOException();
		} 
		catch ( SecurityException | IOException e) {
		}
	}
	public void info(String msg){
		LOGGER.info(msg);
	}
	public void warning(String msg){
		LOGGER.warning(msg);
	}
}
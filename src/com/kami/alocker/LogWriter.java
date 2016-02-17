package com.kami.alocker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Kami
 */
public class LogWriter {
    
    /**
     * Simple log file writer
     * @param message
     * @throws IOException 
     */
    public void writeMessage(String message) throws IOException{
        try(PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter("status.log", true)))) {
            log.println(new Date() + " " +message);
        }catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}

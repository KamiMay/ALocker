package com.kami.alocker;

import java.util.Date;

/**
 *
 * @author Kami
 */
public class PCLogin {
    private String ip;
    private Date firstAttempt;
    private int attempts;
    
    /**
     * When creating new object we specify IP, give it current time and 1 used attempt to login
     * @param ip 
     */
    public PCLogin(String ip){
        this.ip = ip;
        firstAttempt = new Date();
        attempts = 1;
    }
    
    /**
     * Check if certain amount of time has passed since the last time user tried to login
     * @return 
     */
    public boolean checkTimeFrame(){
        boolean ret = false;
        if((new Date().getTime() - firstAttempt.getTime()) >= ALocker.getInstance().getTimeout()*60*1000){
            attempts = 0;
            ret = true;
        }
        return ret;
    }
    
    /**
     * Return amount of attempts user made after checking if the time out for attempts ran out
     * @return 
     */
    public int getAttempts(){
        checkTimeFrame();
        return attempts;
    }
    
    /**
     * Add an attempt 
     */
    public void addAttempt(){
        checkTimeFrame();
        attempts++;
    }
    
    /**
     * Return IP address of this object
     * @return 
     */
    public String getIP(){
        return ip;
    }
    
}

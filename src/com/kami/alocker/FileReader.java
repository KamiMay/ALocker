package com.kami.alocker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kami
 */
public class FileReader implements Runnable {

    private ArrayList<PCLogin> logins = new ArrayList();
    private Filter filter = new Filter();
    private IPManager ipManager = new IPManager();
    private LogWriter log = new LogWriter();

    /**
     * Thread which listens to auth.log changes
     */
    @Override
    public void run() {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String inputStr;
                if ((inputStr = bufReader.readLine()) != null) {
                    //If authentication failed 
                    if (filter.checkAuthFailed(inputStr)) {
                        String ip = filter.extractIP(inputStr).get(0);
                        //Check if we already have an object with this IP with attempts to login
                        PCLogin login = checkIfIPInList(ip);
                        //If result is null, we create new attempt to login and add it to array list where we track all the login attempts 
                        if (login == null) {
                            login = new PCLogin(ip);
                            logins.add(login);
                            //If the IP exists in our array we update attempts count and check if we reacjed max limit of logins attempts
                        } else {
                            //Add new attempt to the count
                            login.addAttempt();
                            //If there were more than 3 attempts block the IP
                            if (login.getAttempts() >= ALocker.getInstance().getAttempts()) {
                                log.writeMessage("Limit: " + ip);
                                ipManager.blockIP(ip);
                                logins.remove(login);
                                cleanArray();
                            }
                        }
                        //If it was possible break in attempt
                    } else if (filter.checkBreakin(inputStr)) {
                        String ip = getIP(inputStr);
                        log.writeMessage("Breakin: " + ip);
                        //Block IP instantly
                        ipManager.blockIP(ip);
                        //Check if IP was added to the list of attemted logins and remove it from the list
                        PCLogin login = checkIfIPInList(ip);
                        if (login != null) {
                            logins.remove(login);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Check if there is an entry with the IP in the list of login attempts
     *
     * @param ip
     * @return
     */
    public PCLogin checkIfIPInList(String ip) {
        PCLogin ret = null;
        for (PCLogin login : logins) {
            if (login.getIP().equals(ip)) {
                ret = login;
            }
        }
        return ret;
    }

    /**
     * Sometimes there is a reverse IP in the string we make sure we have either
     * one or if we have two we grab second IP which is not reversed.
     * @param inputStr
     * @return
     */
    public String getIP(String inputStr) {
        String ip;
        if (filter.extractIP(inputStr).size() > 1) {
            ip = filter.extractIP(inputStr).get(1);
        } else {
            ip = filter.extractIP(inputStr).get(0);
        }
        return ip;
    }

    /**
     * Remove login attempts if they timed out
     */
    public void cleanArray() {
        for(Iterator<PCLogin> iterator = logins.iterator(); iterator.hasNext();){
            if(iterator.next().checkTimeFrame()){
                iterator.remove();
            }
        }
    }
}

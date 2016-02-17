package com.kami.alocker;

import java.io.IOException;

/**
 *
 * @author Kami
 */
public class IPManager {    
    /**
     * Block connections from specified IP address.
     * @param ip 
     */
    public void blockIP(String ip){
        Process p;
        try {
                String command = "iptables -I INPUT 1 -s "+ip+" -j DROP";
                p = Runtime.getRuntime().exec(command);
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                System.err.println(e.getMessage());
        }
    }
}

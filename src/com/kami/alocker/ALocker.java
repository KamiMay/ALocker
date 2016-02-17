package com.kami.alocker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 *
 * @author Kami
 * Authentication Locker
 */
public class ALocker {

    private static ALocker instance = null;
    private int timeout;
    private int attempts;
    private String pattern;

    /**
     * Creating singleton of this class
     * @return 
     */
    public static ALocker getInstance() {
        if (ALocker.instance == null) {
            ALocker.instance = new ALocker();
        }
        return ALocker.instance;
    }

    /**
     * Start the security tool process
     */
    public void run() {
        FileReader reader = new FileReader();
        //Start listening to the log file
        reader.run();
    }

    /**
     * Reading in settings from the config file, which is outside the jar, inside
     * same directory
     * @throws IOException
     * @throws URISyntaxException 
     */
    public void readSettings() throws IOException, URISyntaxException {
        File base = new File(ALocker.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
        File config= new File(base,"app.config");
        FileInputStream fis = new FileInputStream(config);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts[0].equals("attempts")) {
                this.attempts = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("timeout")) {
                this.timeout = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("pattern")) {
                this.pattern = parts[1];
            }
        }
        br.close();
    }

    /**
     * Get timeout limit
     * @return 
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Get attempts limit
     * @return 
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Get pattern match
     * @return 
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        ALocker app = ALocker.getInstance();
        app.readSettings();
        app.run();
    }

}

package com.kami.alocker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Kami
 */
public class Filter {
    
    /**
     * Check if incoming string has Authentication failure message
     * @param authString
     * @return 
     */
    public boolean checkAuthFailed(String authString){
        boolean ret = false;
        if(authString.matches(ALocker.getInstance().getPattern())){
            ret = true;            
        }
        return ret;
    }
    
    /**
     * Check if incoming string has break-in attempt message
     * @param authString
     * @return 
     */
    public boolean checkBreakin(String authString){
        boolean ret = false;
        if(authString.contains("POSSIBLE BREAK-IN ATTEMPT!")){
            ret = true;
        }
        return ret;
    }
    
    /**
     * Extract IP from the string
     * @param logString
     * @return 
     */
    public ArrayList<String> extractIP(String logString){
       ArrayList<String> ret = new ArrayList();
       String pattern = "\\d{1,3}(?:\\.\\d{1,3}){3}";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(logString);
        while (matcher.find()) {
            ret.add(matcher.group());
        }
        return ret;
    }
    
}

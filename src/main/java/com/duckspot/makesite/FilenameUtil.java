package com.duckspot.makesite;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameUtil {
    
    public static void mkdirFor(File f) {
        if (f.exists()) {
            return;
        }
        File p = f.getParentFile();
        if (!p.exists()) {                        
            p.mkdirs();
        }
    }
    
    private static final 
            Pattern findFilename = Pattern.compile("^([^\\.]*)");
    
    public static String getFilename(String filenameExt) {
        Matcher m = findFilename.matcher(filenameExt);
        boolean found = m.find();
        if (found && m.groupCount() >= 1)
            return m.group(1);
        else
            return filenameExt;
    }
    
    private static final 
            Pattern findExt = Pattern.compile("\\.([^\\.]+)?$");
    
    /**
     * Get the last extension from a filename.
     * 
     * @param filenameExt
     * @return the last filename extension including the '.' before the 
     * extension, or "" if no extension exists.
     */
    public static String getLastExt(String filenameExt) {
        Matcher m = findExt.matcher(filenameExt);
        boolean found = m.find();
        if (found && m.groupCount() >= 1)
            return m.group(1);
        else
            return "";
    }
    
    public static String removeLastExt(String filenameExt) {
        Matcher m = findExt.matcher(filenameExt);
        boolean found = m.find();
        if (found && m.groupCount() >= 1) {
            int len = filenameExt.length() - m.group(1).length();
            return filenameExt.substring(0, len-1);
        } else {
            return filenameExt;
        }
    }    
}

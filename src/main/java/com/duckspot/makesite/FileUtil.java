/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.makesite;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Peter.Dobson
 */
public class FileUtil {
    
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
            Pattern findFilenameExt = Pattern.compile("^(.*)(\\.[^.]+)$");
    public static String getFilename(String filenameExt) {
        Matcher m = findFilenameExt.matcher(filenameExt);
        boolean found = m.find();
        if (found && m.groupCount() >= 1)
            return m.group(1);
        else
            return filenameExt;
    }
}

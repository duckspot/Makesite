package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    static Translator[] translators = {new MarkdownTranslator()};
        
    static void build(File dstDir, File srcDir, boolean subDir) {
        for (File srcFile: srcDir.listFiles()) {
            if (srcFile.isDirectory()) {
                if (subDir) {
                    build(srcFile, new File(dstDir, srcFile.getName()), true);
                }
            } else {
                long srcModified = srcFile.lastModified();
                for (Translator t: translators) {
                    if (t.matches(srcFile)) {
                        File dstFile = t.getDstFile(dstDir, srcFile);                        
                        if (dstFile.lastModified() < srcModified) {
                            System.out.println("translate " + 
                                    srcFile + " -> " + dstFile);
                            try {
                                t.translate(dstFile, srcFile);
                            } catch (IOException ex) {
                                Logger.getLogger(App.class.getName())
                                        .log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }                
            }
        }
    }
    
    public static void main( String[] args )
    {
        // TODO: 3 allow args to specify alternate directories to use
        // TODO: 2 scan all .md files in current directory and call maybeTranslate for each
        // TODO: 1 scan all files in current directory and print out names
        File srcDir = new File("src/main/resources");
        File dstDir = new File("target/website");
        build(dstDir, srcDir, true);
    }
}

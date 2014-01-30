package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Translate implements ChangeListener {
    
    private static Translator[] translators = {};
    
    public static void setTranslators(Translator[] translators) {
        Translate.translators = translators;
    }
    
    public static void translateFile(File dstDir, File srcFile) 
            throws IOException
    {
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
        
    private final File dstDir;
    private final File srcDir;    
    private final boolean watch;
    
    public Translate(File dstDir, File srcDir, boolean watch) {
        this.dstDir = dstDir;
        this.srcDir = srcDir;
        this.watch = watch;
    }
    
    private void innerTranslate(File dstDir, File srcDir) throws IOException {
        for (File srcFile: srcDir.listFiles()) {
            if (srcFile.isDirectory()) {                
                File dstSubDir = new File(dstDir, srcFile.getName());
                File srcSubDir = srcFile;
                innerTranslate(dstSubDir, srcSubDir);
            } else {
                translateFile(dstDir, srcFile);
            }
        }
    }
    
    void translate() throws IOException {
        Watch w = null;
        if (watch) {
            w = new Watch(srcDir);
            w.addChangeListener(this);
            w.setup();
        }
        innerTranslate(dstDir, srcDir);
        if (w != null) {
            w.watch();
        }
    }

    @Override
    public void changeOccured() {
        try {
            innerTranslate(dstDir, srcDir);
        } catch (IOException ex) {
            throw new Error("unexpected IOException", ex);
        }
    }
}

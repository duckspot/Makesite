package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TranslateDirectory implements ChangeListener {
    
    public static void translateFile(File dstDir, File srcFile) 
            throws IOException
    {
        TranslationSeries ts = 
                new TranslationSeries(dstDir.toPath(), srcFile.toPath());
        if (ts.needsTranslation()) {
            ts.translate();
        }
    }
    
    private final File dstDir;
    private final File srcDir;    
    private final boolean watch;
    
    TranslateDirectory(Path dstDir, Path srcDir, boolean watch) {
        this.dstDir = dstDir.toFile();
        this.srcDir = srcDir.toFile();
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
        WatchDirectory w = null;
        if (watch) {
            w = new WatchDirectory(srcDir);
            w.addChangeListener(this);
            w.setup();
        }
        innerTranslate(dstDir, srcDir);
        if (w != null) {
            System.out.println("watching for file changes...");
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

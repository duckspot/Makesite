package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Watch {
    
    private final Path srcDir;
    private final WatchService watcher = FileSystems.getDefault().newWatchService();
    private final List<ChangeListener> 
            changeListeners = new ArrayList<ChangeListener>();
            
    public Watch(Path srcDir) throws IOException {        
        this.srcDir = srcDir;
    }
    
    public Watch(File srcDir) throws IOException {
        this(srcDir.toPath());
    }
    
    public void addChangeListener(ChangeListener listener) {
        synchronized(changeListeners) {
            changeListeners.add(listener);
        }
    }
    
    public void removeChangeListener(ChangeListener listener) {
        synchronized(changeListeners) {
            changeListeners.remove(listener);
        }
    }
        
    private void trigger() {
        synchronized(changeListeners) {
            ChangeListener[] tmp = changeListeners.toArray(new ChangeListener[0]);
        }
        for (ChangeListener listener: changeListeners)
        {
            listener.changeOccured();
        }
    }
    
    private void addDir(Path dir) throws IOException {
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
        for (Path child: ds) {
             if (Files.isDirectory(child)) {
                 child.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
             }
        }
    }
    private void removeDir(Path dir) {        
    }
    
    private void handle(WatchEvent<?> event) throws IOException {
        WatchEvent.Kind<?> kind = event.kind();
        if (kind == OVERFLOW) {
            return;
        }
        WatchEvent<Path> ev = (WatchEvent<Path>)event;
        Path name = ev.context();
        Path srcChild = srcDir.resolve(name);
        if (Files.isDirectory(srcChild)) {
            if (kind == ENTRY_CREATE) {
                addDir(srcChild);
            }
            else if (kind == ENTRY_DELETE) {
                removeDir(srcChild);
            }
        } else {        
            trigger();
        }
    }
    
    public void setup() throws IOException {
        addDir(srcDir);
    }
    
    public void watch() throws IOException {

        while (true) {            
            WatchKey key;
            try {
                key = watcher.take();
                for (WatchEvent<?> event: key.pollEvents()) {
                    handle(event);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }            
            } catch (InterruptedException ex) {
                // ignore
            }
        }
        watcher.close();
    }
}

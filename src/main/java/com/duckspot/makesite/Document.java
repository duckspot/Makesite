package com.duckspot.makesite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Document extends HashMap<String,Object> {
            
    private static String getTitle(Path srcFile) {
        String name = FilenameUtil.getFilename(srcFile.getFileName().toString());
        if (name.equals("index")) {
            name = srcFile.getParent().getFileName().toString();
        }
        return name.replaceAll("-|_", " ");
    }
    
    void read(Path srcFile) throws IOException {        
        put("content",new String(Files.readAllBytes(srcFile), "UTF-8"));
        put("title", getTitle(srcFile));
    }
    
    void write(Path dstFile) throws IOException {
        String content = (String)get("content");
        Files.write(dstFile, content.getBytes());
    }
}

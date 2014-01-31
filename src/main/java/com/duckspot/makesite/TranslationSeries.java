package com.duckspot.makesite;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationSeries {

    private static TranslatorRegistry allTranslators;
    
    public static void setRegistry(TranslatorRegistry tr) {
        allTranslators = tr;
    }
    
    private static String titleFor(Path path) {
        String name;
        if (Files.isDirectory(path)) {
            name = path.getFileName().toString();
        } else {
            name = FilenameUtil.getFilename(path.getFileName().toString());            
        }
        return name.replaceAll("-|_", " ");
    }
    
    private final List<Translator> myTranslators = new ArrayList<>();
    private final List<Path> myPaths = new ArrayList<>();
    
        
    TranslationSeries(Path dstDir, Path srcPath) {
        myPaths.add(srcPath);
        String fileExtExt = srcPath.getFileName().toString();
        do {
            String lastExt = FilenameUtil.getLastExt(fileExtExt);
            Translator t = allTranslators.getTranslator(lastExt);
            if (t == null) {
                break;                
            }
            myTranslators.add(t);
            fileExtExt = FilenameUtil.removeLastExt(fileExtExt) + t.getDstExt();
            myPaths.add(dstDir.resolve(fileExtExt));
        } while (true);
        if (myTranslators.isEmpty() && !srcPath.startsWith(dstDir)) {            
            myPaths.add(dstDir.resolve(fileExtExt));
        }
    }
    
    Path getSrcPath() {
        return myPaths.get(0);
    }
    
    Path getDstPath() {
        return myPaths.get(myPaths.size()-1);
    }
    
    boolean needsTranslation() throws IOException {
        Path srcPath = getDstPath();
        if (!Files.exists(srcPath)) {
            return false;
        }
        Path dstPath = getDstPath();        
        if (!Files.exists(dstPath)) {
            return true;
        }
        FileTime srcLastModifiedTime = Files.getLastModifiedTime(getSrcPath());
        FileTime dstLastModifiedTime = Files.getLastModifiedTime(getDstPath());
        return srcLastModifiedTime.compareTo(dstLastModifiedTime) > 0;        
    }
    
    void translate() throws IOException {
        Document document = new Document();
        document.read(getSrcPath());
        for (Translator t: myTranslators) {
            t.translate(document);
        }
        document.write(getDstPath());
    }        
}

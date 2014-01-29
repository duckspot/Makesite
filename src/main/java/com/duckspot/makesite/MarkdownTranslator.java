package com.duckspot.makesite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.markdown4j.Markdown4jProcessor;

/**
 * Translate .md and .markdown files to .html files, using markdown4j
 * 
 * @author Peter Dobson <peter@duckspot.com>
 */
public class MarkdownTranslator implements Translator {

    private static final Pattern match = 
            Pattern.compile("(\\.md|\\.markdown)$");
    private static final String replace = ".html";
    
    public boolean matches(File srcFile) {
        return match.matcher(srcFile.getName()).find();
    }

    public File getDstFile(File srcDir, File srcFile) {
        String name = srcFile.getName();
        Matcher m = match.matcher(name);
        name = m.replaceFirst(replace);
        return new File(srcDir, name);
    }

    private static String getTitle(File srcFile) {
        String name = FileUtil.getFilename(srcFile.getName());
        if (name.equals("index")) {
            name = srcFile.getParentFile().getName();
        }
        return name.replace('-', ' ');
    }
    
    public void translate(File dstFile, File srcFile) throws IOException {
        String text = new Markdown4jProcessor().process(srcFile);
        FileUtil.mkdirFor(dstFile);
        PrintWriter out = new PrintWriter(dstFile);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+getTitle(srcFile)+"</title>");
        out.println("</head>");
        out.println("<body>");
        out.write(text);
        out.println("</body>");
        out.println("</html>");
        out.close();
    }    
}

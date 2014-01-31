package com.duckspot.makesite;

import java.io.IOException;
import org.markdown4j.Markdown4jProcessor;

/**
 * Translate .md and .markdown files to .html files, using markdown4j
 * 
 * @author Peter Dobson <peter@duckspot.com>
 */
public class MarkdownTranslator implements Translator {

    private static final Markdown4jProcessor markdown = new Markdown4jProcessor();
    
    @Override
    public void translate(Document doc) throws IOException {
        doc.put("content",markdown.process((String)doc.get("content")));
        Layout.layout(doc);
    }
    
    @Override
    public String getDstExt() {
        return ".html";
    }
}

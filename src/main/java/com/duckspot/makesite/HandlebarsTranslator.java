package com.duckspot.makesite;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;

/**
 * Translate .hb and .handlebars files using jknack/handlebars 
 * 
 * @author Peter Dobson <peter@duckspot.com>
 */
public class HandlebarsTranslator implements Translator {

    private static final Handlebars handlebars = new Handlebars();
    
    @Override
    public void translate(Document doc) throws IOException {
        Template template = handlebars.compileInline((String)doc.get("content"));
        doc.put("content",template.apply(doc));        
    }
    
    @Override
    public String getDstExt() {
        return "";
    }
}

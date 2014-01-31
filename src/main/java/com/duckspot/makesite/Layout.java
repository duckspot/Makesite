package com.duckspot.makesite;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Layout content and title using _layout.hb template (or default template
 * built into this code.)
 *
 * @author Peter Dobson <peter@duckspot.com>
 */
public class Layout {

    private static final Handlebars handlebars = new Handlebars();

    private static Template layout;

    public static void setSrcDir(Path srcDir) throws IOException {
        Path layoutFile = srcDir.resolve("_layout");
        if (Files.exists(layoutFile) && Files.isReadable(layoutFile)) {
            String t = new String(Files.readAllBytes(layoutFile),"UTF-8");
            layout = handlebars.compileInline(t);
        }            
    }
    private static Template getLayout() throws IOException {
        if (layout == null) {
            layout = handlebars.compileInline(
                    "<html>\n"
                    + "<head><title>{{title}}</title></head>\n"
                    + "<body>{{content}}</body>\n"
                    + "</html>"
            );
        }
        return layout;
    }

    public static void layout(Document document) throws IOException {
        document.put("content", getLayout().apply(document));
    }
}

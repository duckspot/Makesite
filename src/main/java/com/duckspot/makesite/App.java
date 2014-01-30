package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        boolean watch = false;
        File srcDir = new File("src/main/resources");
        File dstDir = new File("target/website");        
        
        for (String arg: args) {
            if (arg.equals("--watch")) {
                watch = true;
            }
        }
        
        Translate.setTranslators(new Translator[] {
            new MarkdownTranslator()
        });
        
        new Translate(dstDir, srcDir, watch).translate();
    }
}

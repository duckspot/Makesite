package com.duckspot.makesite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        final Path currentDir = Paths.get(".");
        final Path mavenSrcDir = Paths.get("src/main/resources");
        boolean isMaven = Files.isDirectory(mavenSrcDir);
        boolean watch = false;
        Path srcDir = null;
        Path dstDir = null;
        
        // process arguments
        for (int i=0; i<args.length; i++) {
            String arg = args[i];
            if (arg.equals("--watch")) {
                watch = true;
            }
            else if (arg.equals("-d") && i+1 < args.length) {
                dstDir = Paths.get(args[++i]);
            }
            else {
                srcDir = Paths.get(arg);
            }
        }
        
        // if dstDir undefined and isMaven attempt to setup maven dstDir
        if (dstDir == null && isMaven) {
            dstDir = Paths.get("target/website");
            Files.createDirectories(dstDir);
            // if mavenDstDir fails then set isMaven to false
            isMaven = Files.isDirectory(dstDir);
            if (!isMaven) {                
                // and make dstDir null so it's defaulted below
                dstDir = null;
            }
        }
        // set srcDir to mavenSrcDir if isMaven still true
        if (srcDir == null && isMaven) {
            srcDir = mavenSrcDir;
        }
        // default srcDir if it's not already set -- to current directory
        if (srcDir == null) {
            srcDir = Paths.get(".");
        }
        // default dstDir if it's not already set -- to same as srcDir
        if (dstDir == null) {            
            dstDir = srcDir;
        }
        
        // setup set of translators
        Translate.setTranslators(new Translator[] {
            new MarkdownTranslator()
        });
        
        // perform translation & watch as appropriate
        new Translate(dstDir, srcDir, watch).translate();
    }
}

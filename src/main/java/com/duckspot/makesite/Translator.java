package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;

interface Translator {

    boolean matches(File srcFile);
    File getDstFile(File srcDir, File srcFile);
    void translate(File dstFile, File srcFile) throws IOException;
}
package com.duckspot.makesite;

import java.io.IOException;

public interface Translator {

    String getDstExt();
    void translate(Document doc) throws IOException;
}
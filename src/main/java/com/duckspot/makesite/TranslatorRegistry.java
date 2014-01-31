package com.duckspot.makesite;

import java.util.HashMap;
import java.util.Map;

public class TranslatorRegistry {
    
    Map<String,Translator> translators = new HashMap<>();
    
    public void addTranslator(String ext, Translator t) {
        translators.put(ext,t);
    }
    
    public Translator getTranslator(String ext) {
        return translators.get(ext);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.makesite;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author Peter.Dobson
 */
public class MarkdownTranslatorTest extends TestCase {
    
    public MarkdownTranslatorTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of matches method, of class MarkdownTranslator.
     */
    public void testMatches() {
        System.out.println("matches");
        File srcFile = new File("a");
        MarkdownTranslator instance = new MarkdownTranslator();
        boolean expResult = false;
        boolean result = instance.matches(srcFile);
        assertEquals(expResult, result);
        srcFile = new File("a.md");
        expResult = true;
        result = instance.matches(srcFile);
        assertEquals(expResult, result);
        srcFile = new File("a.markdown");
        expResult = true;
        result = instance.matches(srcFile);
        assertEquals(expResult, result);
        srcFile = new File("a.mdx");
        expResult = false;
        result = instance.matches(srcFile);
        assertEquals(expResult, result);
        srcFile = new File("a.markdown.bak");
        result = instance.matches(srcFile);
    }

    /**
     * Test of getDstFile method, of class MarkdownTranslator.
     */
    public void testGetDstFile() {
        System.out.println("getDstFile");
        File srcDir = new File(".");
        File srcFile = new File("index.md");
        MarkdownTranslator instance = new MarkdownTranslator();
        File expResult = new File(".\\index.html");
        File result = instance.getDstFile(srcDir, srcFile);
        assertEquals(expResult, result);        
    }

    /**
     * Test of translate method, of class MarkdownTranslator.
     */
    public void testTranslate() throws IOException {
        System.out.println("translate - no test");
        File dstFile = null;
        File srcFile = null;
        MarkdownTranslator instance = new MarkdownTranslator();
//        instance.translate(dstFile, srcFile);        
    }
}

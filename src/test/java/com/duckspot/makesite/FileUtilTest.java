/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.duckspot.makesite;

import java.io.File;
import junit.framework.TestCase;

/**
 *
 * @author Peter.Dobson
 */
public class FileUtilTest extends TestCase {
    
    public FileUtilTest(String testName) {
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
     * Test of mkdirFor method, of class FileUtil.
     */
    public void testMkdirFor() {
        System.out.println("mkdirFor");
        File f = new File("target/website/foo.html");
        FileUtil.mkdirFor(f);
        assertTrue(f.getParentFile().exists());
    }

    /**
     * Test of getFilename method, of class FileUtil.
     */
    public void testGetFilename() {
        System.out.println("getFilename");
        String filenameExt = "index.md";
        String expResult = "index";
        String result = FileUtil.getFilename(filenameExt);
        assertEquals(expResult, result);        
    }
    
}

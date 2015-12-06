/*
 * The MIT License
 *
 * Copyright 2015 Marcio Fonseca.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.anywarelabs.algorithms.datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcio Fonseca
 */
public class MedianHeapTest {
    
    public MedianHeapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class MedianHeap.
     */
    /*@Test
    public void testAdd() {
        System.out.println("add");
        Object item = null;
        MedianHeap instance = new MedianHeap();
        instance.add(item);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getMedian method, of class MedianHeap.
     */
    @Test
    public void testGetMedian() {
        System.out.println("getMedian");
        long[] values = processInput("Median-small1.txt");
        
        Object expResult = 25;
        Object result = getMedianSum(values) % 10000;
        assertEquals(expResult, result);
        
        values = processInput("Median-small2.txt");
        expResult = 75;
        result = getMedianSum(values) % 10000;
        assertEquals(expResult, result);
        
        values = processInput("Median.txt");
        result = getMedianSum(values) % 10000;
        System.out.println("Median sum: " + result);
    }
    
    private int getMedianSum(long[] values) {
        MedianHeap<Long> instance = new MedianHeap<>();
        int sum = 0;
        
        for (long val : values) {
            instance.add(val);
            sum += instance.getMedian();
        }
        
        return sum;
    }
    
    private long[] processInput(String file) {
        
        InputStream in = MedianHeapTest.class.getResourceAsStream(file);
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            String line;
            List<Long> inputList = new ArrayList<>();
            
            while((line = reader.readLine()) != null) {
                
                inputList.add(Long.valueOf(line));
            }
            
            long[] input = new long[inputList.size()];
            
            for (int i=0; i<inputList.size(); i++) {
                input[i] = inputList.get(i);
            }
            
            return input;
        
        } catch (IOException ex) {
            Logger.getLogger(MedianHeapTest.class.getName())
                    .log(Level.INFO, "message", ex);
        }
        
        return null;
    }
    
}

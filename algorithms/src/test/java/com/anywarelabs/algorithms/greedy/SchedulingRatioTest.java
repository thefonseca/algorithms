/*
 * The MIT License
 *
 * Copyright 2015 marciofonseca.
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

package com.anywarelabs.algorithms.greedy;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marciofonseca
 */
public class SchedulingRatioTest {
    
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
     * Test of compare method, of class SchedulingRatio.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        
        SchedulingRatio instance = new SchedulingRatio();
        Scheduling.Job o1 = instance.new Job("4 2");
        Scheduling.Job o2 = instance.new Job("3 2");
        
        int expResult = -1;
        int result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        
        o1 = instance.new Job("4 2");
        o2 = instance.new Job("4 2");
        
        expResult = 0;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        
        o1 = instance.new Job("4 2");
        o2 = instance.new Job("8 4");
        
        expResult = 0;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);
    }
            
    /**
     * Test of getWeightedCompletionTime method, of class SchedulingRatio.
     */
    @Test
    public void testGetWeightedCompletionTime() {
        System.out.println("getWeightedCompletionTime");
        
        SchedulingRatio instance = new SchedulingRatio();
        
        instance.addJob(instance.new Job("2 1"));
        int expResult = 2;
        assertEquals(expResult, instance.getWeightedCompletionTime());
        
        instance.addJob(instance.new Job("3 1"));
        expResult = 7;
        assertEquals(expResult, instance.getWeightedCompletionTime());
        
        instance.addJob(instance.new Job("4 2"));
        expResult = 23;
        assertEquals(expResult, instance.getWeightedCompletionTime());
    }
    
}

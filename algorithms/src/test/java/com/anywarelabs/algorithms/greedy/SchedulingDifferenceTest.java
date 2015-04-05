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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marciofonseca
 */
public class SchedulingDifferenceTest {
    
    public SchedulingDifferenceTest() {
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
     * Test of compare method, of class SchedulingDifference.
     */
    @Test
    public void testCompare() {
        SchedulingDifference instance = new SchedulingDifference();
        Scheduling.Job o1 = instance.new Job("4 2");
        Scheduling.Job o2 = instance.new Job("3 2");
        
        int expResult = -1;
        int result = instance.compare(o1, o2);
        assertEquals("1", expResult, result);
        
        o1 = instance.new Job("4 2");
        o2 = instance.new Job("4 2");
        
        expResult = 0;
        result = instance.compare(o1, o2);
        assertEquals("2", expResult, result);
        
        o1 = instance.new Job("4 2");
        o2 = instance.new Job("6 4");
        
        expResult = 1;
        result = instance.compare(o1, o2);
        assertEquals("3", expResult, result);
    }
    
}

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
package com.anywarelabs.algorithms.greedy;

import com.anywarelabs.algorithms.datastructures.Graph;
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
public class KruskalMSTTest {
    
    public KruskalMSTTest() {
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
     * Test of getMST method, of class KruskalMST.
     */
    @Test
    public void testGetMST() {
        System.out.println("getMST");
        
        Graph g = new Graph(KruskalMST.class.getResourceAsStream("edges2.txt"));
        Graph result = KruskalMST.getMST(g);
        
        Integer expResult = 113;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = new Graph(KruskalMST.class.getResourceAsStream("edges3.txt"));
        result = KruskalMST.getMST(g);
        
        expResult = 89;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = new Graph(KruskalMST.class.getResourceAsStream("edges1.txt"));
        result = KruskalMST.getMST(g);
        System.out.println("MST cost: " + result.getTotalEdgeCost());
    }
    
}

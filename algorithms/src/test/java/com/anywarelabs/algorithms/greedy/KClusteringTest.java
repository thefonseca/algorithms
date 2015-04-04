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
import com.anywarelabs.algorithms.datastructures.KCluster;
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
public class KClusteringTest {
    
    public KClusteringTest() {
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
     * Test of getKCluster method, of class KClustering.
     */
    @Test
    public void testGetKCluster() {
        System.out.println("getMST");
        
        Graph g = new Graph(MaxSpacingKClustering.class.getResourceAsStream("edges2.txt"));
        KCluster result = MaxSpacingKClustering.getKCluster(g, 2);
        
        Integer expResult = 67;
        assertEquals(expResult, result.getSpacing());
        
        g = new Graph(MaxSpacingKClustering.class.getResourceAsStream("edges3.txt"));
        result = MaxSpacingKClustering.getKCluster(g, 3);
        
        expResult = 20;
        assertEquals(expResult, result.getSpacing());
        
        result = MaxSpacingKClustering.getKCluster(g, 2);
        
        expResult = 45;
        assertEquals(expResult, result.getSpacing());
        
        g = new Graph(MaxSpacingKClustering.class.getResourceAsStream("clustering1.txt"));
        result = MaxSpacingKClustering.getKCluster(g, 4);
        System.out.println("Cluster spacing: " + result.getSpacing());
    }
    
}

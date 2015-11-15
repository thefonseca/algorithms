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

import com.anywarelabs.algorithms.datastructures.KCluster;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marcio Fonseca
 */
public class MaxSpacingKClusteringTest {
    
    public MaxSpacingKClusteringTest() {
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
     * Test of getKClusterBinaryStrings method, of class MaxSpacingKClustering.
     */
    @Test
    public void testGetKClusterBinaryStrings() {
        
        InputStream in = MaxSpacingKClusteringTest.class.getResourceAsStream("clustering_small2.txt");
        KCluster result = MaxSpacingKClustering.getKClusterBinaryStrings(in, 2);
        
        int expResult = 2;
        assertEquals(expResult, result.getClusterCount());
        
        in = MaxSpacingKClusteringTest.class.getResourceAsStream("clustering_small.txt");
        result = MaxSpacingKClustering.getKClusterBinaryStrings(in, 2);
        
        expResult = 2;
        assertEquals(expResult, result.getClusterCount());
        
        in = MaxSpacingKClusteringTest.class.getResourceAsStream("clustering_big.txt");
        result = MaxSpacingKClustering.getKClusterBinaryStrings(in, 3);
        
        System.out.println("Max number of k: " + result.getClusterCount());
    }
    
}

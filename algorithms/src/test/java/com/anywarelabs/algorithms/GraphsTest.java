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
package com.anywarelabs.algorithms;

import com.anywarelabs.algorithms.datastructures.Graph;
import com.anywarelabs.algorithms.greedy.Scheduling;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marcio Fonseca
 */
public class GraphsTest {
    
    public GraphsTest() {
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
     * Test of getMinimumCut method, of class Graphs.
     */
    @Test
    public void testGetMinimumCut() {
        System.out.println("getMinimumCut");
        InputStream in = GraphsTest.class.getResourceAsStream("kargerMinCut_small_2.txt");
        Graph g = processInput(in);
        Graph result = Graphs.getMinimumCut(g);
        Assert.assertEquals(2, result.getEdges().size());
        
        in = GraphsTest.class.getResourceAsStream("kargerMinCut.txt");
        g = processInput(in);
        result = Graphs.getMinimumCut(g);
        System.out.println("Min cut: " + result.getEdges().size());
    }
    
    private Graph processInput(InputStream in) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            Graph g = new Graph();
            String line;
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split("( )+|\t");
                
                for (int i=1; i<lineSplit.length; i++) {
                    
                    Graph.Edge edge = g.new Edge(
                            Integer.parseInt(lineSplit[0]) - 1,
                            Integer.parseInt(lineSplit[i]) - 1, 1);
                    
                    Graph.Edge inverseEdge = g.new Edge(edge.getOther(edge.getEither()), edge.getEither(), edge.getCost());
                    
                    if (!g.getEdges().contains(inverseEdge) && !g.getEdges().contains(edge)) {
                        g.addEdge(edge);
                    }
                    Logger.getLogger(Scheduling.class.getName()).fine(edge.toString());
                }
            }
            
            return g;
            
        } catch(IOException ex) {
            Logger.getLogger(GraphsTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}

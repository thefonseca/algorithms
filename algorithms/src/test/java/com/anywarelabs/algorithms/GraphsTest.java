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

import com.anywarelabs.algorithms.datastructures.DirectedGraph;
import com.anywarelabs.algorithms.datastructures.Graph;
import com.anywarelabs.algorithms.datastructures.KCluster;
import com.anywarelabs.algorithms.datastructures.UndirectedGraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
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
        Graph g = processInputMinCut(in);
        Graph result = Graphs.getMinimumCut(g);
        Assert.assertEquals(2, result.getEdges().size());
        
        in = GraphsTest.class.getResourceAsStream("kargerMinCut.txt");
        g = processInputMinCut(in);
        result = Graphs.getMinimumCut(g);
        System.out.println("Min cut: " + result.getEdges().size());
    }
    
    /**
     * Test of getMST method, of class Graphs.
     */
    @Test
    public void testKruskalGetMST() {
        System.out.println("getKruskalMST");
        
        UndirectedGraph g = processInputMST(GraphsTest.class.getResourceAsStream("edges2.txt"));
        Graph result = Graphs.getKruskalMST(g);
        
        Integer expResult = 113;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("edges3.txt"));
        result = Graphs.getKruskalMST(g);
        
        expResult = 89;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("edges1.txt"));
        result = Graphs.getKruskalMST(g);
        System.out.println("MST cost: " + result.getTotalEdgeCost());
    }
    
    /**
     * Test of getMST method, of class Graphs.
     */
    @Test
    public void testPrimGetMST() {
        System.out.println("getPrimMST");
        
        UndirectedGraph g = processInputMST(GraphsTest.class.getResourceAsStream("edges2.txt"));
        Graph result = Graphs.getPrimMST(g);
        
        Integer expResult = 113;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("edges3.txt"));
        result = Graphs.getPrimMST(g);
        
        expResult = 89;
        assertEquals(expResult, result.getTotalEdgeCost());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("edges1.txt"));
        result = Graphs.getPrimMST(g);
        System.out.println("MST cost: " + result.getTotalEdgeCost());
    }
    
    /**
     * Test of getKCluster method, of class Graphs.
     */
    @Test
    public void testGetKCluster() {
        System.out.println("getMST");
        
        UndirectedGraph g = processInputMST(GraphsTest.class.getResourceAsStream("edges2.txt"));
        KCluster result = Graphs.getKCluster(g, 2);
        
        Integer expResult = 67;
        assertEquals(expResult, result.getSpacing());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("edges3.txt"));
        result = Graphs.getKCluster(g, 3);
        
        expResult = 20;
        assertEquals(expResult, result.getSpacing());
        
        result = Graphs.getKCluster(g, 2);
        
        expResult = 45;
        assertEquals(expResult, result.getSpacing());
        
        g = processInputMST(GraphsTest.class.getResourceAsStream("clustering1.txt"));
        result = Graphs.getKCluster(g, 4);
        System.out.println("Cluster spacing: " + result.getSpacing());
    }
    
    /**
     * Test of getStronglyConnectedComponents method, of class Graphs.
     */
    @Test
    public void testGetStronglyConnectedComponents() {
        
        int sccOutputCount = 5;
        
        DirectedGraph g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC-small1.txt"));
        List<List<Integer>> sccs = Graphs.getStronglyConnectedComponents(g);
        String result = getSCCSizesString(sccs, sccOutputCount);
        assertEquals("3,3,3,0,0", result);
        
        g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC-small2.txt"));
        sccs = Graphs.getStronglyConnectedComponents(g);
        result = getSCCSizesString(sccs, sccOutputCount);
        assertEquals("3,3,2,0,0", result);
        
        g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC-small3.txt"));
        sccs = Graphs.getStronglyConnectedComponents(g);
        result = getSCCSizesString(sccs, sccOutputCount);
        assertEquals("3,3,1,1,0", result);
        
        g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC-small4.txt"));
        sccs = Graphs.getStronglyConnectedComponents(g);
        result = getSCCSizesString(sccs, sccOutputCount);
        assertEquals("7,1,0,0,0", result);
        
        g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC-small5.txt"));
        sccs = Graphs.getStronglyConnectedComponents(g);
        result = getSCCSizesString(sccs, sccOutputCount);
        assertEquals("6,3,2,1,0", result);
        
        g = processInputSCC(GraphsTest.class.getResourceAsStream("SCC.txt"));
        sccs = Graphs.getStronglyConnectedComponents(g);
        result = getSCCSizesString(sccs, sccOutputCount);
        System.out.printf("Largest SCCs: %s", result);
    }
    
    /**
     * Test of dijkstra method, of class Graphs.
     */
    @Test
    public void testDijkstra() {
        
        DirectedGraph g = processInputDijkstra(GraphsTest.class.getResourceAsStream("dijkstraData-small1.txt"));
        Integer[] costs = Graphs.dijkstra(g, 0);
        String result = getDistancesString(costs, 1, 2, 3, 4);
        assertEquals("0,3,3,5", result);
        
        g = processInputDijkstra(GraphsTest.class.getResourceAsStream("dijkstraData-small2.txt"));
        costs = Graphs.dijkstra(g, 0);
        result = getDistancesString(costs, 1, 2, 3, 4);
        assertEquals("0,3,4,5", result);
        
        g = processInputDijkstra(GraphsTest.class.getResourceAsStream("dijkstraData-small3.txt"));
        costs = Graphs.dijkstra(g, 0);
        result = getDistancesString(costs, 1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("0,1,2,3,4,4,3,2", result);
        
        g = processInputDijkstra(GraphsTest.class.getResourceAsStream("dijkstraData.txt"));
        costs = Graphs.dijkstra(g, 0);
        result = getDistancesString(costs, 7, 37, 59, 82, 99, 115, 133, 165, 188, 197);
        System.out.println("Shortest paths: " + result);
    }
    
    private String getDistancesString(Integer[] distances, int... vertices) {
        
        StringBuilder builder = new StringBuilder("");
        
        for (int i=0; i<vertices.length; i++) {
            if (i>0) {
                builder.append(",");
            }
            
            if (distances[vertices[i]-1] == null) {
                builder.append(1000000);
                
            } else {
                builder.append(distances[vertices[i]-1]);
            }
        }
        
        return builder.toString();
    }
    
    private String getSCCSizesString(List<List<Integer>> sccs, int count) {
        String result = "";
        
        List<List<Integer>> ordered = new ArrayList<>();
        
        for (List<Integer> component: sccs) {
            ordered.add(new ComparableArrayList(component));
        }
        
        Collections.sort(ordered, Collections.reverseOrder());
        
        for (int i=0; i<count && i<ordered.size(); i++) {
            
            if (i > 0) {
                result += ",";
            }
            
            result += ordered.get(i).size();
        }
        
        for (int i=0; i<(count - ordered.size()); i++) {
            
            result += ",0";
        }
        
        return result;
    }
    
    private DirectedGraph processInputDijkstra(InputStream in) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            DirectedGraph g = new DirectedGraph();
            String line;
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split("( )+|\t");
                
                for (int i=1; i<lineSplit.length; i++) {
                    
                    String[] edgeSplit = lineSplit[i].split(",");
                    
                    DirectedGraph.DirectedEdge edge = g.createEdge(
                            Integer.parseInt(lineSplit[0]) - 1,
                            Integer.parseInt(edgeSplit[0]) - 1, 
                            Integer.parseInt(edgeSplit[1]));
                    
                    if (!g.getEdges().contains(edge)) {
                        g.addEdge(edge);
                    }
                    //Logger.getLogger(GraphsTest.class.getName()).fine(edge.toString());
                }
            }
            
            return g;
            
        } catch(IOException ex) {
            Logger.getLogger(GraphsTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private Graph processInputMinCut(InputStream in) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            Graph g = new UndirectedGraph();
            String line;
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split("( )+|\t");
                
                for (int i=1; i<lineSplit.length; i++) {
                    
                    Graph.Edge edge = g.createEdge(
                            Integer.parseInt(lineSplit[0]) - 1,
                            Integer.parseInt(lineSplit[i]) - 1, 1);
                    
                    Graph.Edge inverseEdge = edge.getReverse();
                    
                    if (!g.getEdges().contains(inverseEdge) && !g.getEdges().contains(edge)) {
                        g.addEdge(edge);
                    }
                    //Logger.getLogger(GraphsTest.class.getName()).fine(edge.toString());
                }
            }
            
            return g;
            
        } catch(IOException ex) {
            Logger.getLogger(GraphsTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private UndirectedGraph processInputMST(InputStream in) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            String line = reader.readLine();
            Integer vertexCount;
            
            if (line.contains(" ")) {
                String[] lineSplit = line.split(" ");
                vertexCount = Integer.parseInt(lineSplit[0]);
                //Integer edgeCount = Integer.parseInt(lineSplit[1]);
            
            } else {
                vertexCount = Integer.parseInt(line);
            }
            
            UndirectedGraph g = new UndirectedGraph(vertexCount);
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split(" ");
                
                UndirectedGraph.UndirectedEdge edge = g.createEdge(Integer.parseInt(lineSplit[0]) - 1,
                                    Integer.parseInt(lineSplit[1]) - 1, 
                                    Integer.parseInt(lineSplit[2]));
                
                g.addEdge(edge);
                //Logger.getLogger(GraphsTest.class.getName()).fine(edge.toString());
            }
            
            return g;
            
        } catch(IOException ex) {
            Logger.getLogger(UndirectedGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private DirectedGraph processInputSCC(InputStream in) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            DirectedGraph g = new DirectedGraph();
            String line;
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split(" ");
                
                DirectedGraph.DirectedEdge edge = g.createEdge(
                        Integer.parseInt(lineSplit[0]) - 1,
                        Integer.parseInt(lineSplit[1]) - 1, 1);
                
                g.addEdge(edge);
                //Logger.getLogger(GraphsTest.class.getName()).fine(edge.toString());
            }
            
            return g;
            
        } catch(IOException ex) {
            Logger.getLogger(UndirectedGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    class ComparableArrayList extends ArrayList implements Comparable<ComparableArrayList> {

        public ComparableArrayList(Collection c) {
            super(c);
        }
        
        @Override
        public int compareTo(ComparableArrayList o) {
            
            if (o == null) {
                return 0;
            }
            
            return Integer.valueOf(this.size()).compareTo(o.size());
        }
        
    }
}

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
import com.anywarelabs.algorithms.datastructures.Graph.Edge;
import java.util.Random;

/**
 *
 * @author Marcio Fonseca
 */
public class Graphs {
    
    /**
     * Karger's minimum cut algorithm implementation. Repeats the algorithm 
     * "reps" times, where "reps" is a function of the input size.
     * For more details See https://en.wikipedia.org/wiki/Karger%27s_algorithm.
     * 
     * @param g source graph
     * @return 
     */
    public static Graph getMinimumCut(Graph g) {
        int vertexCount = g.getVertexCount();
        //int reps = (int) (vertexCount * vertexCount * Math.log(vertexCount));
        int reps = (int) (vertexCount * 2);
        return getMinimumCut(g, reps);
    }
    
    /**
     * Karger's minimum cut algorithm implementation. Randomly chooses an edge
     * to contract until the graph has only two vertices. By increasing the 
     * number of repetitions we can increase the probability of finding the 
     * minimum cut.
     * For more details See https://en.wikipedia.org/wiki/Karger%27s_algorithm.
     * 
     * @param g source graph
     * @param reps number of repetitions
     * @return 
     */
    public static Graph getMinimumCut(Graph g, long reps) {
        
        Graph minCut = null;
        
        for (int i=0; i<reps; i++) {
            
            Graph auxGraph = new Graph();
            Random rand = new Random();
            
            // copy g to auxGraph
            g.getEdges().stream().forEach((e) -> {
                auxGraph.addEdge(e);
            });
            
            while (auxGraph.getVertexCount() > 2) {

                int numEdges = auxGraph.getEdges().size();
                
                if (numEdges <= 1) {
                    break;
                }
                
                int chosenEdgeIndex = rand.nextInt(numEdges - 1) + 1;
                Edge chosenEdge = auxGraph.getEdges().get(chosenEdgeIndex);
                auxGraph.contractEdge(chosenEdge);
            }
            
            if (minCut == null || 
                    minCut.getEdges().size() > auxGraph.getEdges().size()) {
                minCut = auxGraph;
            }
        
            if (i % 100 == 0) {
                System.out.printf("Repetition %d: min cut = %d\n", i, 
                        minCut.getEdges().size());
            }
        }
        
        return minCut;
    }
    
}

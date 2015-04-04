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
import com.anywarelabs.algorithms.datastructures.Graph.Edge;
import com.anywarelabs.algorithms.datastructures.KCluster;
import java.util.Collections;
import java.util.List;

/**
 * Max Spacing k-order Cluster based on Kruskal MST algorithm.
 * 
 * @author Marcio Fonseca
 */
public class MaxSpacingKClustering {
    
    public static KCluster getKCluster(Graph g, int k) {
        
        List<Edge> edges = g.getEdges();
        Collections.sort(edges);
        
        KCluster cluster = new KCluster(g.getVertices().size(), k);
        
        for (Edge edge : edges) {
            
            Integer either = edge.getEither();
            Integer other = edge.getOther(either);
            
            if (!cluster.connected(either, other)) {
                
                if (cluster.getSpacing() != null) {
                    break;
                }
                
                cluster.union(either, other, edge.getCost());
            }
            
        }
        
        return cluster;
    }
}

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
import com.anywarelabs.algorithms.datastructures.UnionFind;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Marcio Fonseca
 */
public class KruskalMST {
    
    public static Graph getMST(Graph g) {
        
        UnionFind unionFind = new UnionFind(g.getVertices().size());
        
        List<Graph.Edge> edges = g.getEdges();
        Collections.sort(edges);
        
        Graph mst = new Graph(g.getVertices().size());
        
        for (Graph.Edge edge : edges) {
            
            Integer either = edge.getEither();
            Integer other = edge.getOther(either);
            
            if (!unionFind.connected(either, other)) {
                
                if (unionFind.getUnionCount() >= g.getVertices().size() - 1) {
                    break;
                }
                
                unionFind.union(either, other);
                mst.addEdge(edge);
            }
        }
        
        return mst;
    }
    
}

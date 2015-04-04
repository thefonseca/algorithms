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

import com.anywarelabs.algorithms.datastructures.Graph;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author marciofonseca
 */
public class PrimMST {
    
    public static Graph getMST(Graph g) {
     
        Graph mst = new Graph(g.getVertices().size());
        mst.addVertex(0, mst.new Vertex());
        
        Queue<Graph.Edge> queue = new PriorityQueue<>();
        
        for (Graph.Edge edge : g.getVertices().get(0).getEdges()) {
            queue.add(edge);
        }
        
        while (queue.size() > 0) {
            
            Graph.Edge edge = queue.remove();
            Integer either = edge.getEither();
            Integer other = edge.getOther(either);
            
            if (mst.hasVertex(either) && mst.hasVertex(other)) {
                continue;
            }
            
            Integer vertexToAdd = mst.hasVertex(either) ? other : either;
            
            mst.addEdge(edge);
            
            for (Graph.Edge _edge : g.getVertices().get(vertexToAdd).getEdges()) {
                
                if (!_edge.equals(edge)) {
                    queue.add(_edge);
                }
            }
        }
        
        return mst;
    }
}

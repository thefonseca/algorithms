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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcio Fonseca
 */
public abstract class GraphSearch {
    
    protected List<Integer> order;
    //protected Map<Integer, Boolean> visited;
    boolean[] visited;
    private final Graph graph;
    
    public GraphSearch(Graph g) {
        graph = g;
        //visited = new HashMap<>();
        visited = new boolean[g.size()];
        
        for (int i=0; i<visited.length; i++) {
            visited[i] = false;
        }
    }

    public final void search(int startVertex) {
        
        order = new ArrayList<>();
        //Logger.getLogger(GraphSearch.class.getName()).log(Level.INFO, "DFS for vertex: " + startVertex);
        
        resetState();
        
        visit(startVertex);
        Integer currentVertex;
        
        while((currentVertex = currentVertex()) != null) {
            
            List<Integer> notVisited = getNotVisited(currentVertex);
            
            if (notVisited.isEmpty()) {
                endVisit();
            
            } else {
                
                for (Integer connected: notVisited) {
                    visit((int) connected);
                }
                
            }
        }
    }
    
    private List<Integer> getNotVisited(int vertex) {
        
        List<Integer> notVisited = new ArrayList<>();
        
        for (Object connected: graph.getConnectedVertices(vertex)) {

            if (!visited((Integer) connected)) {
                notVisited.add((Integer) connected);
            }
        }
        
        return notVisited;
    }

    private boolean visited(Integer connected) {
        //return visited.containsKey(connected);
        return visited[connected];
    }

    public List<Integer> getOrder() {
        return order;
    }

    private void visit(int vertex) {
        order.add(vertex);
        //visited.put(vertex, Boolean.TRUE);
        visited[vertex] = true;
        startVisit(vertex);
    }
    
    public abstract Integer currentVertex();
    protected abstract void startVisit(int vertex);
    protected abstract void endVisit();
    protected abstract void resetState();
}

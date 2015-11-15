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

package com.anywarelabs.algorithms.datastructures;

import com.anywarelabs.algorithms.datastructures.Graph.Edge;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marciofonseca
 * @param <EdgeType>
 */
public abstract class Graph<EdgeType extends Edge> implements Comparable<Graph> {
    
    public class Vertex {
        List<EdgeType> edges;
        
        public void addEdge(EdgeType edge) {
            
            if (edges == null) {
                edges = new ArrayList<>();
            }
            
            edges.add(edge);
        }
        
        public boolean removeEdge(EdgeType edge) {
            
            if (edges != null) {
                return edges.remove(edge);
            }
            
            return false;
        }

        public List<EdgeType> getEdges() {
            
            if (edges == null) {
                edges = new ArrayList<>();
            }
            //return new ArrayList<>(edges);
            return edges;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Vertex: \n");
            
            for (Edge e : getEdges()) {
                builder.append(e.toString());
                builder.append("\n");
            }
            
            return builder.toString();
        }
    }
    
    public abstract class Edge implements Comparable<EdgeType> {
        protected Integer cost;

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }
        
        public abstract boolean containsVertex(int label);
        public abstract EdgeType getReverse();
        
        @Override
        public int compareTo(EdgeType that) {
            
            if (this.cost == null) {
                return 0;
            }
            
            return this.cost.compareTo(that.cost);
        }
    }
    
    List<Vertex> vertices;
    List<EdgeType> edges;
    Integer totalEdgeCost;
    int vertexCount = 0;
    
    public Graph() {
    }
    
    public Graph(int vertexCount) {
        
        vertices = new ArrayList<>(vertexCount);
            
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(null);
        }
    }
    
    public abstract void addEdge(EdgeType edge);
    
    public abstract EdgeType createEdge(Integer x, Integer y, Integer cost);
    
    public abstract void contractEdge(EdgeType edge);
    
    public abstract boolean removeEdge(EdgeType edge);
    
    public abstract List<Integer> getConnectedVertices(int vertex);
    
    public final void addVertex(Integer label, Vertex vertex) {
        
        int size = vertices().size();
        if (label >= size) {
            for (int i=0; i<=(label-size); i++) {
                vertices().add(null);
            }
        }
        
        vertexCount++;
        vertices().set(label, vertex);
    }
    
    public final void removeVertex(int label) {
        
        if (vertices() != null) {
            
            List<EdgeType> edgesToRemove =  new ArrayList<>();
            
            for (EdgeType e: edges) {
                
                if (e.containsVertex(label)) {
                    edgesToRemove.add(e);
                }
            }
            
            for (EdgeType e: edgesToRemove) {
                removeEdge(e);
            }
            
            vertexCount--;
            vertices().set(label, null);
        }
    }
    
    public boolean hasVertex(int vertex) {
        
        if (vertices().size() > vertex) {
            return vertices().get(vertex) != null;
        }
        
        return false;
    }
    
    public Vertex getVertex(int vertex) {
        
        if (vertices().size() > vertex) {
            return vertices().get(vertex);
        }
        
        return null;
    }

    private List<Vertex> vertices() {
        
        if (vertices == null) {
            vertices = new ArrayList<>();
        }
        
        return vertices;
    }
    
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices());
    }
    
    public int size() {
        
        /*int count = 0;
        
        for (Vertex v: vertices()) {
            
            if (v != null) {
                count++;
            }
        }
        
        return count;*/
        return vertexCount;
    }
    
    public List<EdgeType> getEdges() {
        if (edges != null) {
            return new ArrayList<>(edges);
        }
        
        return new ArrayList<>();
    }
    
    public Integer getTotalEdgeCost() {
        return totalEdgeCost;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Graph: \n");

        for (Vertex v : vertices()) {
            builder.append(v.toString());
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public int compareTo(Graph o) {
        return Integer.valueOf(this.vertices().size()).compareTo(
                o.vertices().size());
    }
}

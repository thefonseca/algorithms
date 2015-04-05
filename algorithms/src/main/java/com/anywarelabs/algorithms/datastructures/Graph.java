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

import com.anywarelabs.algorithms.greedy.Scheduling;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marciofonseca
 */
public class Graph {
    
    public class Vertex {
        Set<Edge> edges;
        
        public void addEdge(Edge edge) {
            
            if (edges == null) {
                edges = new HashSet<>();
            }
            
            edges.add(edge);
        }

        public Set<Edge> getEdges() {
            return new HashSet<>(edges);
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Vertex: \n");
            
            for (Edge e : edges) {
                builder.append(e.toString());
                builder.append("\n");
            }
            
            return builder.toString();
        }
    }
    
    public class Edge implements Comparable<Edge> {
        private final Integer x;
        private final Integer y;
        private final Integer cost;

        public Edge(Integer x, Integer y, Integer cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        public Integer getEither() {
            return x;
        }

        public Integer getOther(int vertex) {
            return vertex == x ? y : x;
        }

        public Integer getCost() {
            return cost;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Edge: ");
            Integer vertex = getEither();
            builder.append(vertex + 1);
            builder.append(" -- ");
            builder.append(getOther(vertex) + 1);
            builder.append("; cost = ");
            builder.append(getCost());
            return builder.toString();
        }

        @Override
        public int compareTo(Edge that) {
            
            if (this.cost == null) {
                return 0;
            }
            
            return this.cost.compareTo(that.cost);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            
            final Edge other = (Edge) obj;
            
            if (!Objects.equals(this.x, other.x)) {
                return false;
            }
            
            if (!Objects.equals(this.y, other.y)) {
                return false;
            }
            
            return Objects.equals(this.cost, other.cost);
        }
        
        

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.x);
            hash = 97 * hash + Objects.hashCode(this.y);
            hash = 97 * hash + Objects.hashCode(this.cost);
            return hash;
        }
    }
    
    List<Vertex> vertices;
    List<Edge> edges;
    Integer totalEdgeCost;
    
    public Graph() {
    }
    
    public Graph(int vertexCount) {
        
        vertices = new ArrayList<>(vertexCount);
            
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(null);
        }
    }
    
    public Graph(InputStream in) {
        
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
            
            vertices = new ArrayList<>(vertexCount);
            
            for (int i = 0; i < vertexCount; i++) {
                vertices.add(null);
            }
            
            while((line = reader.readLine()) != null) {
                
                String[] lineSplit = line.split(" ");
                
                Edge edge = new Edge(Integer.parseInt(lineSplit[0]) - 1,
                                    Integer.parseInt(lineSplit[1]) - 1, 
                                    Integer.parseInt(lineSplit[2]));
                
                addEdge(edge);
                Logger.getLogger(Scheduling.class.getName()).fine(edge.toString());
                
            }
            
        } catch(IOException ex) {
            Logger.getLogger(Scheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public final void addEdge(Edge edge) {
        
        if (vertices == null) {
            vertices = new ArrayList<>();
        }
        
        Vertex vertex = null;
        Integer either = edge.getEither();
        
        if (vertices.size() > either) {
            vertex = vertices.get(either);
        
        }
        
        if (vertex == null) {
            vertex = new Vertex();
            vertices.set(either, vertex);
        }
        
        vertex.addEdge(edge);
        
        Integer other = edge.getOther(either);
        vertex = null;
        
        if (vertices.size() > other) {
            vertex = vertices.get(other);
        }
                
        if (vertex == null) {
            vertex = new Vertex();
            vertices.set(other, vertex);
        }

        vertex.addEdge(edge);
        
        if (totalEdgeCost == null) {
            totalEdgeCost = 0;
        }
        
        totalEdgeCost += edge.getCost();
        
        if (edges == null) {
            edges = new ArrayList<>();
        }
        
        edges.add(edge);
    }
    
    public final void addVertex(Integer label, Vertex vertex) {
        
        if (vertices == null) {
            vertices = new ArrayList<>();
        }
        
        vertices.add(label, vertex);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }
    
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }
    
    public boolean hasVertex(int vertex) {
        
        if (getVertices().size() > vertex) {
            return getVertices().get(vertex) != null;
        }
        
        return false;
    }
    
    public Vertex getVertex(int vertex) {
        
        if (getVertices().size() > vertex) {
            return getVertices().get(vertex);
        }
        
        return null;
    }
    
    public Integer getTotalEdgeCost() {
        return totalEdgeCost;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Graph: \n");

        for (Vertex v : vertices) {
            builder.append(v.toString());
            builder.append("\n");
        }

        return builder.toString();
    }
    
    public static void main(String[] args) {
        
        InputStream in = Graph.class
                .getResourceAsStream("edges1.txt");
        
        new Graph(in);
    }
}

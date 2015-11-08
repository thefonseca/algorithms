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
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marciofonseca
 */
public class Graph {
    
    public class Vertex {
        List<Edge> edges;
        
        public void addEdge(Edge edge) {
            
            if (edges == null) {
                edges = new ArrayList<>();
            }
            
            edges.add(edge);
        }
        
        public boolean removeEdge(Edge edge) {
            
            if (edges != null) {
                return edges.remove(edge);
            }
            
            return false;
        }

        public List<Edge> getEdges() {
            
            if (edges == null) {
                edges = new ArrayList<>();
            }
            return new ArrayList<>(edges);
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
        
        public Integer getOther(Integer vertex) {
            return vertex.equals(x) ? y : x;
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
        
        if (totalEdgeCost == null) {
            totalEdgeCost = 0;
        }
        
        if (edges == null) {
            edges = new ArrayList<>();
        }
        
        if (edges.add(edge)) {
            totalEdgeCost += edge.getCost();
        }
        
        Integer either = edge.getEither();
        Vertex vertex = getVertex(either);
        
        if (vertex == null) {
            vertex = new Vertex();
            addVertex(either, vertex);
        }
        
        vertex.addEdge(edge);
        
        Integer other = edge.getOther(either);
        vertex = getVertex(other);
        
        if (vertex == null) {
            vertex = new Vertex();
            addVertex(other, vertex);
        }

        vertex.addEdge(edge);
    }
    
    public final boolean removeEdge(Edge edge) {
        
        //System.out.printf("Removing edge %s\n", edge.toString());
        boolean removed = false;
        
        if (edges != null) {
           removed = edges.remove(edge);
        }
        
        if (removed) {
            int either = edge.getEither();
            int other = edge.getOther(either);

            Vertex vEither = getVertex(either);
            vEither.removeEdge(edge);

            Vertex vOther = getVertex(other);
            vOther.removeEdge(edge);

            totalEdgeCost -= edge.getCost();
        }
        
        //System.out.printf("Removed edge %s (%b)\n", edge.toString(), removed);
        
        return removed;
    }
    
    public final void contractEdge(Edge edge) {
        
        //System.out.println("======================");
        //System.out.println("Vertex count: " + getVertexCount() + " || " + getEdges().size());
        while(removeEdge(edge)){}
        Graph.Edge inverseEdge = new Edge(edge.getOther(edge.getEither()), edge.getEither(), edge.getCost());
        while(removeEdge(inverseEdge)){}
        
        int either = edge.getEither();
        int other = edge.getOther(either);
        
        //Vertex vEither = getVertex(either);
        Vertex vOther = getVertex(other);
        
        for (Edge e: vOther.getEdges()) {
            
            Edge newEdge;
            
            if (e.getEither().equals(other)) {
                newEdge = new Edge(either, e.getOther(other), e.getCost());
                
            } else {
                newEdge = new Edge(either, e.getEither(), e.getCost());
            }
            
            //System.out.printf("Adding edge %s\n", newEdge.toString());
            addEdge(newEdge);
        }
        
        //System.out.printf("Removing vertex %d\n", other+1);
        removeVertex(other);
    }
    
    public final void addVertex(Integer label, Vertex vertex) {
        
        int size = vertices().size();
        if (label >= size) {
            for (int i=0; i<=(label-size); i++) {
                vertices().add(null);
            }
        }
        
        vertices().set(label, vertex);
    }
    
    public final void removeVertex(int label) {
        
        if (vertices() != null) {
            
            Vertex vertex = getVertex(label);
            
            for (Edge e: vertex.getEdges()) {
                removeEdge(e);
            }
            
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
        
        /*List<Vertex> list = new ArrayList<>();
        
        for (Vertex v: vertices()) {
            
            if (v != null) {
                list.add(v);
            }
        }*/
        
        return new ArrayList<>(vertices());
    }
    
    public int getVertexCount() {
        
        int count = 0;
        
        for (Vertex v: vertices()) {
            
            if (v != null) {
                count++;
            }
        }
        
        return count;
    }
    
    public List<Edge> getEdges() {
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
}

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

import com.anywarelabs.algorithms.datastructures.UndirectedGraph.UndirectedEdge;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author marciofonseca
 */
public class UndirectedGraph extends Graph<UndirectedEdge>{
    
    public class UndirectedEdge extends Graph.Edge {
        private final Integer x;
        private final Integer y;
        
        public UndirectedEdge(Integer x, Integer y, Integer cost) {
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
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            
            final UndirectedEdge other = (UndirectedEdge) obj;
            
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

        @Override
        public boolean containsVertex(int label) {
            
            if (x != null && x.equals(label)) {
                return true;
            
            } else if (y != null && y.equals(label)) {
                return true;
            }
            
            return false;
        }

        @Override
        public UndirectedEdge getReverse() {
            return new UndirectedEdge(y, x, cost);
        }
    }
    
    public UndirectedGraph() {
    }
    
    public UndirectedGraph(int vertexCount) {
        super(vertexCount);
    }
    
    @Override
    public void addEdge(UndirectedEdge edge) {
        
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
    
    @Override
    public final boolean removeEdge(UndirectedEdge edge) {
        
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
    
    @Override
    public final void contractEdge(UndirectedEdge edge) {
        
        //System.out.println("======================");
        //System.out.println("Vertex count: " + getVertexCount() + " || " + getEdges().size());
        while(removeEdge(edge)){} // we may have several parallel edges
        UndirectedEdge inverseEdge = edge.getReverse();
        while(removeEdge(inverseEdge)){}
        
        int either = edge.getEither();
        int other = edge.getOther(either);
        
        //Vertex vEither = getVertex(either);
        Vertex vOther = getVertex(other);
        
        for (UndirectedEdge e: vOther.getEdges()) {
            
            UndirectedEdge newEdge;
            
            if (e.getEither().equals(other)) {
                newEdge = new UndirectedEdge(either, e.getOther(other), e.getCost());
                
            } else {
                newEdge = new UndirectedEdge(either, e.getEither(), e.getCost());
            }
            
            //System.out.printf("Adding edge %s\n", newEdge.toString());
            addEdge(newEdge);
        }
        
        //System.out.printf("Removing vertex %d\n", other+1);
        removeVertex(other);
    }
    
    @Override
    public UndirectedEdge createEdge(Integer x, Integer y, Integer cost) {
        return new UndirectedEdge(x, y, cost);
    }
}

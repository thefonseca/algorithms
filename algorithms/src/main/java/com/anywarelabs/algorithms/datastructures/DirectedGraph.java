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

import com.anywarelabs.algorithms.datastructures.DirectedGraph.DirectedEdge;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author marciofonseca
 */
public class DirectedGraph extends Graph<DirectedEdge> {

    public class DirectedEdge extends Graph.Edge {
        private final Integer source;
        private final Integer target;
        
        public DirectedEdge(Integer source, Integer target, Integer cost) {
            this.source = source;
            this.target = target;
            this.cost = cost;
        }

        public Integer getSource() {
            return source;
        }

        public Integer getTarget() {
            return target;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Edge: ");
            builder.append(getSource() + 1);
            builder.append(" -- ");
            builder.append(getTarget() + 1);
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
            
            final DirectedEdge other = (DirectedEdge) obj;
            
            if (!Objects.equals(this.source, other.source)) {
                return false;
            }
            
            if (!Objects.equals(this.target, other.target)) {
                return false;
            }
            
            return Objects.equals(this.cost, other.cost);
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.source);
            hash = 97 * hash + Objects.hashCode(this.target);
            hash = 97 * hash + Objects.hashCode(this.cost);
            return hash;
        }

        @Override
        public boolean containsVertex(int label) {
            
            if (source != null && source.equals(label)) {
                return true;
            
            } else if (target != null && target.equals(label)) {
                return true;
            }
            
            return false;
        }

        @Override
        public DirectedEdge getReverse() {
            return new DirectedEdge(target, source, cost);
        }
    }
    
    public DirectedGraph() {
        super();
    }
    
    public DirectedGraph(int vertexCount) {
        super(vertexCount);
    }
    
    @Override
    public final void addEdge(DirectedEdge edge) {
        
        if (totalEdgeCost == null) {
            totalEdgeCost = 0;
        }
        
        if (edges == null) {
            edges = new ArrayList<>();
        }
        
        if (edges.add(edge)) {
            totalEdgeCost += edge.getCost();
        }
        
        Integer source = edge.getSource();
        Vertex vertex = getVertex(source);
        
        if (vertex == null) {
            vertex = new Vertex();
            addVertex(source, vertex);
        }
        
        vertex.addEdge(edge);
        
        Integer target = edge.getTarget();
        vertex = getVertex(target);
        
        if (vertex == null) {
            vertex = new Vertex();
            addVertex(target, vertex);
        }
    }
    
    @Override
    public final boolean removeEdge(DirectedEdge edge) {
        
        //System.out.printf("Removing edge %s\n", edge.toString());
        boolean removed = false;
        
        if (edges != null) {
           removed = edges.remove(edge);
        }
        
        if (removed) {
            Vertex vEither = getVertex(edge.getSource());
            vEither.removeEdge(edge);
            
            totalEdgeCost -= edge.getCost();
        }
        
        //System.out.printf("Removed edge %s (%b)\n", edge.toString(), removed);
        return removed;
    }
    
    @Override
    public final void contractEdge(DirectedEdge edge) {
        
        //System.out.println("======================");
        //System.out.println("Vertex count: " + getVertexCount() + " || " + getEdges().size());
        while(removeEdge(edge)){} // we may have several parallel edges
        DirectedEdge inverseEdge = edge.getReverse();
        while(removeEdge(inverseEdge)){}
        
        Vertex target = getVertex(edge.getTarget());
        
        for (DirectedEdge e: target.getEdges()) {
            
            DirectedEdge newEdge = new DirectedEdge(edge.getSource(), e.getTarget(), e.getCost());
            //System.out.printf("Adding edge %s\n", newEdge.toString());
            addEdge(newEdge);
        }
        
        //System.out.printf("Removing vertex %d\n", other+1);
        removeVertex(edge.getTarget());
    }
    
    @Override
    public DirectedEdge createEdge(Integer x, Integer y, Integer cost) {
        return new DirectedEdge(x, y, cost);
    }
}

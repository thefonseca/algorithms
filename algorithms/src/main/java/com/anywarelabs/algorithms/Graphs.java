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

import com.anywarelabs.algorithms.datastructures.DirectedGraph;
import com.anywarelabs.algorithms.datastructures.Graph;
import com.anywarelabs.algorithms.datastructures.Graph.Edge;
import com.anywarelabs.algorithms.datastructures.KCluster;
import com.anywarelabs.algorithms.datastructures.UndirectedGraph;
import com.anywarelabs.algorithms.datastructures.UnionFind;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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
        int vertexCount = g.size();
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
            
            Graph auxGraph = new UndirectedGraph();
            Random rand = new Random();
            
            // copy g to auxGraph
            g.getEdges().stream().forEach((e) -> {
                auxGraph.addEdge((Edge) e);
            });
            
            while (auxGraph.size() > 2) {

                int numEdges = auxGraph.getEdges().size();
                
                if (numEdges <= 1) {
                    break;
                }
                
                int chosenEdgeIndex = rand.nextInt(numEdges - 1) + 1;
                Edge chosenEdge = (Edge) auxGraph.getEdges().get(chosenEdgeIndex);
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
    
    public static Graph getKruskalMST(UndirectedGraph g) {
        
        UnionFind unionFind = new UnionFind(g.getVertices().size());
        
        List<UndirectedGraph.UndirectedEdge> edges = g.getEdges();
        Collections.sort(edges);
        
        Graph mst = new UndirectedGraph(g.getVertices().size());
        
        for (UndirectedGraph.UndirectedEdge edge : edges) {
            
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
    
    public static Graph getPrimMST(UndirectedGraph g) {
     
        Graph mst = new UndirectedGraph(g.getVertices().size());
        mst.addVertex(0, mst.new Vertex());
        
        Queue<UndirectedGraph.UndirectedEdge> queue = 
                new PriorityQueue<>(g.getVertices().get(0).getEdges());
        
        while (queue.size() > 0) {
            
            UndirectedGraph.UndirectedEdge edge = queue.remove();
            Integer either = edge.getEither();
            Integer other = edge.getOther(either);
            
            if (mst.hasVertex(either) && mst.hasVertex(other)) {
                continue;
            }
            
            Integer vertexToAdd = mst.hasVertex(either) ? other : either;
            
            mst.addEdge(edge);
            
            for (UndirectedGraph.UndirectedEdge _edge : g.getVertices().get(vertexToAdd).getEdges()) {
                
                if (!_edge.equals(edge)) {
                    queue.add(_edge);
                }
            }
        }
        
        return mst;
    }
    
    public static KCluster getKCluster(UndirectedGraph g, int clusterCount) {
        
        List<UndirectedGraph.UndirectedEdge> edges = g.getEdges();
        Collections.sort(edges);
        
        KCluster cluster = new KCluster(g.getVertices().size(), clusterCount);
        
        for (UndirectedGraph.UndirectedEdge edge : edges) {
            
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
    
    /**
     * Kosaraju-Sharir algorithm to find the strongly connected components of a 
     * directed graph.
     * 
     * @param g
     * @return 
     */
    public static List<List<Integer>> getStronglyConnectedComponents(DirectedGraph g) {
        
        Graphs.reverse(g);
        List<Integer> reversePostOrder = getReversePostOrder(g);
        
        Graphs.reverse(g);
        List<List<Integer>> sccs = new ArrayList<>();
        boolean[] visited = new boolean[g.size()];
        
        for (int i=0; i<visited.length; i++) {
            visited[i] = false;
        }
        
        DFS dfs = new DFS(g);
        
        for (Integer i: reversePostOrder) {
            
            if (!visited[i]) {
                dfs.search(i);
                sccs.add(dfs.getOrder());
                
                for (Integer j: dfs.getOrder()) {
                    visited[j] = true;
                }
            }
        }
        
        return sccs;
    }
    
    private static List<Integer> getReversePostOrder(DirectedGraph g) {
        
        List<Integer> postOrder = new ArrayList<>();
        boolean[] visited = new boolean[g.size()];
        
        for (int i=0; i<visited.length; i++) {
            visited[i] = false;
        }
        
        DFS dfs = new DFS(g);
        
        for (int i=0; i<g.size(); i++) {
            
            if (!visited[i]) {
                dfs.search(i);
                postOrder.addAll(dfs.getPostOrder());
                
                for (Integer j: dfs.getOrder()) {
                    visited[j] = true;
                }
            }
        }
        
        Collections.reverse(postOrder);
        return postOrder;
    }
    
    public static void reverse(DirectedGraph g) {
        
        for (DirectedGraph.DirectedEdge e: g.getEdges()) {
            g.reverseEdge(e);
        }
    }
}

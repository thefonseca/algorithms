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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Max Spacing k-order Cluster based on Kruskal MST algorithm.
 * 
 * @author Marcio Fonseca
 */
public class MaxSpacingKClustering {
    
    public static KCluster getKCluster(Graph g, int clusterCount) {
        
        List<Edge> edges = g.getEdges();
        Collections.sort(edges);
        
        KCluster cluster = new KCluster(g.getVertices().size(), clusterCount);
        
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
    
    
    public static KCluster getKClusterBinaryStrings(InputStream in, int minSpacing) {
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            String line = reader.readLine();
            
            Map<String, Node> nodeMap = new HashMap<>();
            List<Node> nodes = new ArrayList<>();
            
            int index = 0;
            while((line = reader.readLine()) != null) {
                
                String label = line.replaceAll(" ", "");
                
                if (nodeMap.get(label) == null) {
                    Node node = new Node(label, index);
                    nodes.add(node);
                    nodeMap.put(label, node);
                    index++;
                }
            }
            
            Collections.sort(nodes);
            
            return createCluster(nodes, nodeMap, minSpacing);
            
        } catch(IOException ex) {
            Logger.getLogger(Scheduling.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private static KCluster createCluster(List<Node> nodes, 
            Map<String, Node> nodeMap, int minSpacing) {
        
        KCluster cluster = new KCluster(nodes.size(), 0);
        
        for (Node node : nodes) {
            
            int counter = minSpacing - 1;
            unionNearNodes(node, node, nodeMap, cluster, counter);
        }
        
        return cluster;
    }
    
    private static void unionNearNodes(Node node, Node original, Map<String, Node> nodeMap,
            KCluster cluster, int counter) {
        
        counter--;
        
        for (String bitChange : getBitChanges(node.getLabel())) {
                    
            if (bitChange.compareTo(original.getLabel()) < 0) {
                continue;
            }

            Node bitChangeNode = nodeMap.get(bitChange);
            if (bitChangeNode != null) {
                cluster.union(node.getIndex(), bitChangeNode.getIndex());
            
            } else {
                bitChangeNode = new Node(bitChange, original.getIndex());
            }
            
            if (counter > 0) {
                unionNearNodes(bitChangeNode, original, nodeMap, cluster, counter);
            }
        }
    }
    
    private static List<String> getBitChanges(String str) {
        
        List<String> changes = new ArrayList<>();
        
        char[] c = str.toCharArray();
        
        for (int i = c.length - 1; i >= 0; i--) {
            
            char aux = c[i];
            c[i] = c[i] == '1' ? '0' : '1';
            String s = String.valueOf(c, 0, c.length);
            changes.add(s);
            c[i] = aux;
        }
        
        return changes;
    }
    
    private static class Node implements Comparable<Node> {
        private String label;
        private int index;

        public Node(String label, int index) {
            this.label = label;
            this.index = index;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int Index) {
            this.index = Index;
        }

        @Override
        public int compareTo(Node o) {
            return this.getLabel().compareTo(o.getLabel());
        }
    }
}

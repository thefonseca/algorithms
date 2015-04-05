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

import com.anywarelabs.algorithms.HammingDistance;
import com.anywarelabs.algorithms.datastructures.Graph;
import com.anywarelabs.algorithms.datastructures.Graph.Edge;
import com.anywarelabs.algorithms.datastructures.KCluster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
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
            String[] lineSplit = line.split(" ");
            
            //Integer nodeCount;
            Integer bitCount = Integer.parseInt(lineSplit[1]);
            
            int[] allNodes = new int[(int)Math.pow(2, bitCount)];
            int index = 0;
            
            for (int i = 0; i < allNodes.length; i++) {
                allNodes[i] = -1;
            }
            
            List<Integer> nodeList = new ArrayList<>();
            
            while((line = reader.readLine()) != null) {
                
                StringTokenizer parser = new StringTokenizer(line);
                int count = 0;
                int node = 0;
                
                while (parser.hasMoreTokens()) {
                    node = node ^ (Integer.parseInt(parser.nextToken()) << count++);
                }
                
                if (allNodes[node] < 0) {
                    
                    allNodes[node] = index;
                    nodeList.add(index, node);
                    index++;
                }
            }
            
            int[] nodes = new int[nodeList.size()];
            
            for (int i = 0; i < nodeList.size(); i++) {
                nodes[i] = nodeList.get(i);
            }
            
            return createCluster(nodes, allNodes, bitCount, minSpacing);
            
        } catch(IOException ex) {
            Logger.getLogger(Scheduling.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private static KCluster createCluster(int[] nodes, int[] allNodes,
            int bitCount, int minSpacing) {
        
        KCluster cluster = new KCluster(nodes.length, 1);
        Arrays.sort(nodes);
        
        for (int i = 0; i < nodes.length; i++) {
            
            int counter = minSpacing - 1;
            unionNearNodes(nodes[i], getBitChanges(nodes[i], bitCount),
                    allNodes, cluster, counter);
        }
        
        return cluster;
    }
    
    private static void unionNearNodes(int node, int[] bitChanges, 
            int[] allNodes, KCluster cluster, int counter) {
        
        counter--;
        
        for (int bitChange : bitChanges) {
                    
            if (bitChange < node) {
                continue;
            }
            
            if (allNodes[bitChange] >= 0) {
                cluster.union(allNodes[node], allNodes[bitChange]);
            }
            
            if (counter > 0) {
                unionNearNodes(node, getBitChanges(bitChange,
                        bitChanges.length), allNodes, cluster, counter);
            }
        }
    }
    
    private static int[] getBitChanges(int i, int bitCount) {
        int[] changes = new int[bitCount];
        
        for (int k = 0; k < changes.length; k++) {
            
            changes[k] = i ^ (0x1 << k);
        }
        
        return changes;
    }
    
    public static void main(String[] args) {
        InputStream in = MaxSpacingKClustering.class.getResourceAsStream("clustering_big.txt");
        KCluster result = MaxSpacingKClustering.getKClusterBinaryStrings(in, 3);
        
        System.out.println("Max number of k: " + result.getClusterCount());
    }
}

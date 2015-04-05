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

package com.anywarelabs.algorithms.datastructures;

/**
 *
 * @author Marcio Fonseca
 */
public class UnionFind {
    
    private final int[] elements;
    private final int[] ranks;
    private int unionCount;

    public UnionFind(int count) {
        elements = new int[count];
        ranks = new int[count];
        
        for (int i = 0; i < elements.length; i++) {
            elements[i] = i;
            ranks[i] = 1;
        }
        
        unionCount = 0;
    }
    
    public int find(int a) {
        return root(a);
    }
    
    public void union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);
        
        if (rootA == rootB) {
            return;
        }
        
        if (ranks[rootA] >= ranks[rootB]) {
            elements[rootB] = rootA;
            ranks[rootA] += ranks[rootB];
        
        } else {
            elements[rootA] = rootB;
            ranks[rootB] += ranks[rootA];
        }
        
        unionCount++;
    }
    
    public boolean connected(int a, int b) {
        return root(a) == root(b);
    }

    public int getUnionCount() {
        return unionCount;
    }
    
    public int getElementCount() {
        return elements.length;
    }
    
    private int root(int n) {
        
        while(elements[n] != n) {
            elements[n] = elements[elements[n]];
            n = elements[n];
        }
        
        return n;
    }
    
}

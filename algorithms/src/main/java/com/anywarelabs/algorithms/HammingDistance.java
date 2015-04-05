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

/**
 *
 * @author Marcio Fonseca
 */
public class HammingDistance {
    
    public static int getDistance(String s1, String s2) {
        
        if (s1.length() != s2.length()) {
            throw new IllegalArgumentException("Strings must have equal lengths");
        }
        
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        
        int distance = 0;
        
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) {
                distance++;
            }
        }
        
        return distance;
    }
}

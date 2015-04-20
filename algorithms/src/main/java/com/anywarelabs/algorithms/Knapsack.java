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

import com.anywarelabs.algorithms.greedy.Scheduling;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcio Fonseca
 */
public class Knapsack {

    private int[][] items;
    private int maximumWeight;
    
    private int[][] processInput(InputStream in) {
        
        try (BufferedReader reader = 
                new BufferedReader(new InputStreamReader(in))) {

            String line = reader.readLine();
            String[] lineSplit = line.split(" ");

            maximumWeight = Integer.parseInt(lineSplit[0]);
            int itemCount = Integer.parseInt(lineSplit[1]);

            int[][] allItems = new int[itemCount][2];

            int count = 0;

            while ((line = reader.readLine()) != null) {
                StringTokenizer parser = new StringTokenizer(line);
                allItems[count][0] = Integer.parseInt(parser.nextToken());
                allItems[count][1] = Integer.parseInt(parser.nextToken());
                count++;
            }
            
            return allItems;

        } catch (IOException ex) {
            Logger.getLogger(Scheduling.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int getMaximumValue(InputStream in) {
        
        int[][] allItems = processInput(in);
        return Knapsack.getMaximumValue(allItems, maximumWeight);
    }
    
    public static int getMaximumValue(int[][] items, int maximumWeight) {
        
        int[][] a = new int[items.length + 1][maximumWeight + 1];
        
        for (int i = 1; i <= items.length; i++) {
            for (int w = 0; w <= maximumWeight; w++) {
            
                if (w >= items[i-1][1]) {
                
                    a[i][w] = Math.max(a[i-1][w], 
                            a[i-1][w - items[i-1][1]] + items[i-1][0]);
                
                } else {
                    a[i][w] = a[i-1][w];
                }
            }
        }
        
        return a[items.length][maximumWeight];
    }
}

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

    public static int getMaximumValue(InputStream in) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line = reader.readLine();
            String[] lineSplit = line.split(" ");

            int knapsackSize = Integer.parseInt(lineSplit[1]);
            int itemCount = Integer.parseInt(lineSplit[1]);

            int[][] items = new int[itemCount][2];

            int count = 0;

            while ((line = reader.readLine()) != null) {
                StringTokenizer parser = new StringTokenizer(line);
                items[count][0] = Integer.parseInt(parser.nextToken());
                items[count][1] = Integer.parseInt(parser.nextToken());
                count++;
            }

            return getMaximumValue(items, knapsackSize);

        } catch (IOException ex) {
            Logger.getLogger(Scheduling.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return -1;
    }
    
    public static int getMaximumValue(int[][] items, int knapsackSize) {
        
        int[][] a = new int[items.length][knapsackSize];
        
        for (int i = 0; i < items.length; i++) {
            a[0][i] = 0;
        }
        
        for (int i = 1; i < items.length; i++) {
            for (int j = 0; j < knapsackSize; j++) {
            
                if (j-a[i][1] >= 0) {
                
                    a[i][j] = Math.max(a[i-1][j], a[i-1][j-a[i][1]] + items[i][0]);
                
                } else {
                    a[i][j] = a[i-1][j];
                }
            }
        }
        
        return a[items.length - 1][0];
    }
}

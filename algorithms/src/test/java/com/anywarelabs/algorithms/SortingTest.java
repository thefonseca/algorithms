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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcio Fonseca
 */
public class SortingTest {
    
    public SortingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of mergeSort method, of class Sorting.
     */
    //@Test
    public void testMergeSort() {
        System.out.println("mergeSort");
        
        Sorting instance = new Sorting();
        
        for (int i = 1; i <= 100; i++) {
        
            int listSize = i;
            List<Integer> expected = getSortedList(listSize);
            List<Integer> list = shuffle(expected);

            long result = instance.mergeSort(list);
            
            //System.out.println("Sorted List: " + list);
            //System.out.println("-------------------------------");
            
            assertEquals(expected, list);
        }
        
        InputStream in = Sorting.class.getResourceAsStream("IntegerArray.txt");
        List<Integer> list = processInput(in);
        long result = instance.mergeSort(list);
        System.out.println("Inversions: " + result);
    }
    
    /**
     * Test of quicksort method, of class Sorting.
     */
    @Test
    public void testQuicksort() {
        System.out.println("quicksort");
        
        Sorting instance = new Sorting();
        
        for (int i = 1; i <= 100; i++) {
        
            int listSize = i;
            List<Integer> expected = getSortedList(listSize);
            List<Integer> list = shuffle(expected);

            int[] array = toArray(list);
            long result = instance.quicksort(array, Sorting.PivotStrategy.MEDIAN_OF_THREE);
            
            list = toList(array);
            
            //System.out.println("Sorted List: " + list);
            //System.out.println("-------------------------------");
            
            assertEquals(expected, list);
        }
        
        InputStream in = Sorting.class.getResourceAsStream("QuickSort.txt");
        List<Integer> list = processInput(in);
        
        long result = instance.quicksort(toArray(list), Sorting.PivotStrategy.FIRST);
        System.out.println("Inversions for pivot rule FIRST: " + result);
        
        result = instance.quicksort(toArray(list), Sorting.PivotStrategy.LAST);
        System.out.println("Inversions for pivot rule LAST: " + result);
        
        result = instance.quicksort(toArray(list), Sorting.PivotStrategy.MEDIAN_OF_THREE);
        System.out.println("Inversions for pivot rule MEDIAN_OF_THREE: " + result);
    }
    
    private List shuffle(List list) {
        
        List<Integer> shuffled = new ArrayList<>(list.size());
        
        for (int i = 1; i <= list.size(); i++) {
            shuffled.add(null);
        }
        
        int rand;
        
        for (int i = 1; i <= list.size(); i++) {
            
            while(shuffled.get(rand = new Random().nextInt(list.size())) != null) { }
            
            shuffled.set(rand, i);
        }
        
        //System.out.println("Shuffled List: " + shuffled);
        return shuffled;
    }
    
    private List getSortedList(int size) {
        
        List<Integer> sortedList = new ArrayList<>(size);
        
        for (int i = 1; i <= size; i++) {
            sortedList.add(i);
        }
        
        //System.out.println("Expected List: " + sortedList);
        return sortedList;
    }
    
    private List processInput(InputStream in) {
        
        try (BufferedReader reader = 
                new BufferedReader(new InputStreamReader(in))) {

            String line;
            List list = new ArrayList();

            while ((line = reader.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
            
            return list;

        } catch (IOException ex) {
            Logger.getLogger(SortingTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private int[] toArray(List<Integer> list) {
        
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        
        return array;
    }

    private List toList(int[] array) {
        
        List list = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        
        return list;
    }
    
}

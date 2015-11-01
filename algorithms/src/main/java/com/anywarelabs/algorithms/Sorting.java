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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Marcio Fonseca
 */
public class Sorting {
    
    /**
     * Sorts the specified array into ascending numerical order.
     *
     * <p>Implementation note: The sorting algorithm is a traditional one-pivot
     * Quicksort.
     * 
     * @param array the array to be sorted
     * @return number of comparisons performed
     */
    public int quicksort(int[] array) {
        return quicksort(array, 0, array.length - 1,
                PivotStrategy.MEDIAN_OF_THREE);
    }
    
    /**
     * Sorts the specified array into ascending numerical order.
     * 
     * <p>Implementation note: The sorting algorithm is a traditional one-pivot
     * Quicksort.
     * 
     * @param array
     * @param pivotStrategy rule for choosing pivots
     * @return number of comparisons performed
     */
    public int quicksort(int[] array, PivotStrategy pivotStrategy) {
        return quicksort(array, 0, array.length - 1, pivotStrategy);
    }
    
    private int quicksort(int[] array, int low, int high,
            PivotStrategy pivotStrategy) {
        
        if (low >= high) {
            return 0;
        }
            
        int pivotIndex = choosePivot(array, low, high, pivotStrategy);
        int comparisons = high - low;
        pivotIndex = partition(array, low, high, pivotIndex);
        comparisons += quicksort(array, low, pivotIndex-1, pivotStrategy);
        comparisons += quicksort(array, pivotIndex+1, high, pivotStrategy);
        return comparisons;
    }
    
    /**
     * Returns the index of the pivot, according to the chosen strategy. The 
     * median-of-three strategy offers much better performance when the input 
     * array is nearly sorted.
     * 
     * @param array
     * @param low
     * @param high
     * @param strategy
     * @return the index of the chosen pivot
     */
    private int choosePivot(int[] array, int low, int high,
            PivotStrategy strategy) {
        
        if (strategy.equals(PivotStrategy.FIRST)) {
            return low;
        
        } else if (strategy.equals(PivotStrategy.LAST)) {
            return high;
        
        } else { // MEDIAN_OF_THREE
            
            int middle = (low + high) >>> 1;
            
            List list = Arrays.asList(array[low], array[middle], array[high]);
            mergeSort(list);
            
            int median = (int) list.get(1);
            
            if (median == array[low]) {
                return low;
            
            } 
            
            if (median == array[high]) {
                return high;
            }
            
            return middle;
        }
    }

    /**
     * Partitions the array around the pivot.
     * 
     * @param array
     * @param low
     * @param high
     * @param pivotIndex new index for the pivot
     * @return 
     */
    private int partition(int[] array, int low, int high, int pivotIndex) {
        
        if (pivotIndex > low) {
            // swap with pivot with first element
            swap(array, low, pivotIndex);
        }
        
        int i = low + 1;
        int j = i;
        
        while (j <= high) {
            
            if (array[j] < array[low]) {
                swap(array, j, i);
                i++;
            }
            
            j++;
        }
        
        // place pivot in the right place
        swap(array, i-1, low);
        
        // returns new index for the pivot
        return i-1;
    }
    
    private void swap(int[] array, int i, int j) {
        int aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }
    
    public enum PivotStrategy {
        
        FIRST, LAST, MEDIAN_OF_THREE;
    }
    
    /**
     * Sorts the specified list into ascending order, according to the
     * {@linkplain Comparable natural ordering} of its elements.
     * All elements in the list must implement the {@link Comparable}
     * interface.  Furthermore, all elements in the list must be
     * <i>mutually comparable</i> (that is, {@code e1.compareTo(e2)}
     * must not throw a {@code ClassCastException} for any elements
     * {@code e1} and {@code e2} in the list).
     * 
     * The implementation uses a modified version of merge sort algorithm that
     * counts the number of inversions found in the original list.
     * 
     * @param <T>
     * @param list the list to be sorted.
     * @return the number of inversions found in the original list.
     */
    public <T extends Comparable> long mergeSort(List<T> list) {
        return mergeSortImpl(list, 0, list.size() - 1);
    }
    
    /**
     * Merge sort implementation. The original list parameter is modified to 
     * avoid using additional space.
     * 
     * @param list the list to be sorted.
     * @param start
     * @param end
     * @return the number of inversions found in the original list.
     */
    private <T extends Comparable> long mergeSortImpl(List<T> list, int start, int end) {
        
        if (end - start + 1 == 1) {
            return 0;
        }
        
        int mid = (start + end) >>> 1;
        long leftInversions = mergeSortImpl(list, start, mid);
        long rightInversions = mergeSortImpl(list, mid + 1, end);
        long crossInversions = merge(list, start, mid, end);
     
        return leftInversions + rightInversions + crossInversions;
    }

    /**
     * Merge step for the merge sort algorithm. 
     * 
     * @param list the list to be sorted.
     * @param start
     * @param mid
     * @param end
     * @return 
     */
    private  <T extends Comparable> long merge(List<T> list, int start, int mid,
            int end) {
        
        int i = start;
        int j = mid + 1;
        long inversions = 0;
        
        List merged = new ArrayList(end - start + 1);
        
        for (int k = start; k <= end; k++) {
            merged.add(null);
        }
        
        int mergedIndex = 0;
        
        while (mergedIndex < merged.size() && (i <= mid || j <= end)) {
            
            if (i <= mid && (j > end || list.get(i).compareTo(list.get(j)) <= 0)) {
                merged.set(mergedIndex, list.get(i++));
            
            } else {
                merged.set(mergedIndex, list.get(j++));
                inversions += (mid - i + 1);
            }
            
            mergedIndex++;
        }
        
        for (Iterator it = merged.iterator(); it.hasNext();) {
            T val = (T) it.next();
            list.set(start++, val);
        }
        
        return inversions;
    }
}

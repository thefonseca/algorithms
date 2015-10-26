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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Marcio Fonseca
 */
public class Sorting {
    
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
    private  <T extends Comparable> long merge(List<T> list, int start, int mid, int end) {
        
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

/*
 * The MIT License
 *
 * Copyright 2015 marciofonseca.
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

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author marciofonseca
 */
public class SchedulingDifference extends Scheduling {

    public SchedulingDifference() {
    }

    public SchedulingDifference(InputStream in) {
        super(in);
    }

    public SchedulingDifference(List<Job> jobs) {
        super(jobs);
    }
    
    @Override
    public int compare(Job o1, Job o2) {
        
        Integer diff1 = o1.getWeight() - o1.getLength();
        Integer diff2 = o2.getWeight() - o2.getLength();
        
        int comparison = diff2.compareTo(diff1);
        
        if (comparison == 0) {
            return o2.getWeight().compareTo(o1.getWeight());
        }
        
        return comparison;
    }
    
}

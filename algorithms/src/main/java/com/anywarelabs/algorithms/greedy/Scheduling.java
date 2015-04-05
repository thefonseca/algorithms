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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marciofonseca
 */
public abstract class Scheduling implements Comparator<Scheduling.Job> {

    public class Job {

        private final Integer weight;
        private final Integer length;

        public Job(String line) {
            String[] split = line.split(" ");
            weight = Integer.parseInt(split[0]);
            length = Integer.parseInt(split[1]);
        }

        public Integer getWeight() {
            return weight;
        }

        public Integer getLength() {
            return length;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Job: weight = ");
            builder.append(weight);
            builder.append("; length = ");
            builder.append(length);
            return builder.toString();
        }
    }

    private List<Job> jobs;
    private long weightedCompletionTime;

    public Scheduling() {
    }
    
    public Scheduling(InputStream in) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            
            String line = reader.readLine();
            Integer jobCount = Integer.parseInt(line);
            jobs = new ArrayList<>(jobCount);

            while ((line = reader.readLine()) != null) {
                Job job = new Job(line);
                jobs.add(job);
                
                Logger.getLogger(Scheduling.class.getName()).fine(job.toString());
            }
            
            process();
            
        } catch (IOException ex) {
            Logger.getLogger(Scheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Scheduling(List<Job> jobs) {
        this.jobs = jobs;
        process();
    }
    
    public final void process() {
        
        Logger.getLogger(Scheduling.class.getName())
                .log(Level.FINE, "Processing jobs");
        Collections.sort(jobs, this);
        
        long completionTime = 0;
        weightedCompletionTime = 0;
        
        for (Job job : jobs) {
            completionTime += job.getLength();
            weightedCompletionTime += (job.getWeight() * completionTime);
            
            Logger.getLogger(Scheduling.class.getName()).log(Level.FINE, 
                "Job: {0} -> completion time: {1}; weight completion time: {2}",
                new Object[]{job.toString(), completionTime,
                    String.valueOf(weightedCompletionTime)});
            
        }
    }

    public List<Job> getJobs() {
        return new ArrayList(jobs);
    }
    
    public void addJob(Job job) {
        
        if (jobs == null) {
            jobs = new ArrayList<>();
        }
        
        jobs.add(job);
        process();
    }
    
    public long getWeightedCompletionTime() {
        return weightedCompletionTime;
    }
}

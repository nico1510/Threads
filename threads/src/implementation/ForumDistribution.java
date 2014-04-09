/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author nico
 */
public class ForumDistribution implements Comparable {

    private TreeMap<Integer, Integer> distribution = new TreeMap<Integer, Integer>();

    /**
     * @return the distribution
     */
    public TreeMap<Integer, Integer> getDistribution() {
        return distribution;
    }

    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(TreeMap<Integer, Integer> distribution) {
        this.distribution = distribution;
    }

    public ForumDistribution() {
        this.distribution = new TreeMap<Integer, Integer>();
    }

    public ForumDistribution(TreeMap<Integer, Integer> distribution) {
        this.distribution = distribution;
    }

    public int getNumberOfContentItems() {
        int sum = 0;
        for (Entry e : distribution.entrySet()) {
            sum += (Integer) e.getKey() * (Integer) e.getValue();
        }
        return sum;
    }
    
    public int getNumberOfThreads() {
        int sum = 0;
        for (Entry e : distribution.entrySet()) {
            sum += (Integer) e.getValue();
        }
        return sum;
    }
    
    public void addThread(int threadLength) {
        if (!this.distribution.containsKey(threadLength)) {
            this.distribution.put(threadLength, 1);
        } else {
            this.distribution.put(threadLength, this.distribution.get(threadLength) + 1);
        }
    }

    @Override
    public int compareTo(Object o) {
        ForumDistribution distribution2 = (ForumDistribution) o;
        return -1 * Integer.valueOf(this.getNumberOfThreads()).compareTo(Integer.valueOf(distribution2.getNumberOfThreads()));
    }

    @Override
    public String toString() {
        return "Forum distribution with " + this.getNumberOfContentItems() + " content items";
    }
}

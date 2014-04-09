/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import java.util.ArrayList;

/**
 *
 * @author nico
 */
public class Threads {

    public static ArrayList<Double> threads = new ArrayList<Double>();
    public static ArrayList<Double> threadFrequencies = new ArrayList<Double>();
    public static int missedCount = 0;

    public static void newThread() {
        threads.add(0, 1.0);
        increaseFrequency(1);
    }

    public static double getListSize() {
        return new Double(threads.size());
    }

    public static double getMissedCount() {
        return new Double(missedCount);
    }

    public static void replyTo(int position, int filterID) {
        position--;
        if (filterID == 1) {
            if (position < threads.size()) {
                updateThreads(position);
            } else {
                missedCount++;
            }
        } else if (filterID == 2) {
            int count = -1;
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i) == 1.0) {
                    count++;
                }
                if (count == position) {
                    updateThreads(i);
                    break;
                }
            }
            if (count < position) {
                missedCount++;
            }
        } else if (filterID == 3) {
            int count = -1;
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i) > 1.0) {
                    count++;
                }
                if (count == position) {
                    updateThreads(i);
                    break;
                }
            }
            if (count < position) {
                missedCount++;
            }
        }
    }

    public static void updateThreads(int threadPos) {
        Double replies = threads.remove(threadPos);
        decreaseFrequency(replies.intValue());
        replies += 1.0;
        threads.add(0, replies);
        increaseFrequency(replies.intValue());
    }

    public static void increaseFrequency(int freqPos) {
        // Since 0 isn't a valid Threadlength : Threadfrequencies for Threadlength=1 is at position 0, 
        // for Threadlength=2 at position 1 and so on... therefore freqPos is decreased here
        freqPos--;

        if (freqPos >= threadFrequencies.size()) {
            threadFrequencies.add(freqPos, 1.0);
        } else {
            threadFrequencies.set(freqPos, threadFrequencies.get(freqPos) + 1.0);
        }
    }

    public static void decreaseFrequency(int freqPos) {
        // Since 0 isn't a valid Threadlength : Threadfrequencies for Threadlength=1 is at position 0, 
        // for Threadlength=2 at position 1 and so on... therefore freqPos is decreased here
        freqPos--;
        threadFrequencies.set(freqPos, threadFrequencies.get(freqPos) - 1.0);

    }
}

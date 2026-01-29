package edu.eci.arsw.threads;

public class BlackListControl {
    private int totalOccurrences = 0;
    private boolean stop = false;

    public synchronized boolean stopSearch() {
        return stop;
    }

    public synchronized void reportOccurrence() {
        totalOccurrences++;
        if (totalOccurrences >= 5) {
            stop = true;
        }
    }
    public synchronized int getTotalOccurrences() {
        return totalOccurrences;
    }
}

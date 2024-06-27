package it.polimi.ingsw.am24.view.input;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A thread-safe buffer class that uses a queue to store string data.
 * This class provides methods to add data to the buffer, pop data from the buffer,
 * and clear all data from the buffer.
 */
public class Buffer {
    private final Queue<String> data;

    /**
     * Initializes a new instance of the Buffer class.
     * The internal queue is instantiated as an ArrayDeque.
     */
    public Buffer() {
        data = new ArrayDeque<>();
    }

    /**
     * Adds a new string to the buffer.
     * This method is synchronized to ensure thread safety.
     * After adding the string, it notifies all waiting threads.
     *
     * @param txt The string to add to the buffer.
     */
    public void add(String txt) {
        synchronized (this) {
            data.add(txt);
            this.notifyAll();
        }
    }

    /**
     * Pops a string from the buffer.
     * This method waits if the buffer is empty until a string is available.
     * The method is synchronized to ensure thread safety.
     *
     * @return The string that is popped from the buffer.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public String pop() throws InterruptedException {
        synchronized (this){
            while(data.isEmpty()){this.wait();}
            return data.poll();
        }
    }

    /**
     * Removes all strings from the buffer.
     * This method is synchronized to ensure thread safety.
     * It clears the buffer by polling all elements until it is empty.
     */
    public void popAll(){
        synchronized (this) {
            while (!data.isEmpty()) {
                data.poll();
            }
        }
    }
}


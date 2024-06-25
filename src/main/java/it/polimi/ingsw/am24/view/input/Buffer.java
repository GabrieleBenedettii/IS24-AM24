package it.polimi.ingsw.am24.view.input;

import java.util.ArrayDeque;
import java.util.Queue;

public class Buffer {
    private final Queue<String> data;

    /**
     * init
     */
    public Buffer() {
        data = new ArrayDeque<>();
    }

    public void add(String txt) {
        synchronized (this) {
            data.add(txt);
            this.notifyAll();
        }
    }

    public String pop() throws InterruptedException {
        synchronized (this){
            while(data.isEmpty()){this.wait();}
            return data.poll();
        }
    }

    public void popAll(){
        synchronized (this) {
            while (!data.isEmpty()) {
                data.poll();
            }
        }
    }
}


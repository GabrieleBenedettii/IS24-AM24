package it.polimi.ingsw.am24.view.input;

public class InputReaderGUI implements InputReader {
    private final Buffer buffer;

    public InputReaderGUI() {
        this.buffer = new Buffer();
    }
    public Buffer getBuffer() {return this.buffer;}
    public synchronized void addString(String string) {buffer.add(string);}
}

package it.polimi.ingsw.am24.view.input;
/**
 * The InputReaderGUI class implements the InputReader interface.
 * It provides a buffer to store input data and a method to add strings to the buffer.
 */
public class InputReaderGUI implements InputReader {
    private final Buffer buffer;

    /**
     * Initializes a new instance of the InputReaderGUI class.
     * Creates a new Buffer instance to store input data.
     */
    public InputReaderGUI() {
        this.buffer = new Buffer();
    }

    /**
     * Gets the buffer associated with this InputReaderGUI.
     *
     * @return The buffer from which input data can be read.
     */
    public Buffer getBuffer() {return this.buffer;}

    /**
     * Adds a string to the buffer.
     * This method is synchronized to ensure thread safety.
     *
     * @param string The string to add to the buffer.
     */
    public synchronized void addString(String string) {buffer.add(string);}
}

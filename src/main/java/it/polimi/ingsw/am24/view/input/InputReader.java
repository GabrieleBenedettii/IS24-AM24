package it.polimi.ingsw.am24.view.input;
/**
 * The InputReader interface provides a method to retrieve a buffer.
 * Classes that implement this interface should provide a buffer from which input data can be read.
 */
public interface InputReader {

    /**
     * Gets the buffer associated with this InputReader.
     *
     * @return The buffer from which input data can be read.
     */
    Buffer getBuffer();
}

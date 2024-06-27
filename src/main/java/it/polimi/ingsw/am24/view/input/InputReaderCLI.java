package it.polimi.ingsw.am24.view.input;

import java.util.Scanner;

/**
 * The InputReaderCLI class extends the Thread class and implements the InputReader interface.
 * It reads input from the command line and adds it to a buffer.
 */
public class InputReaderCLI extends Thread implements InputReader {
    private final Buffer buffer = new Buffer();

    /**
     * Initializes a new instance of the InputReaderCLI class.
     * Starts the thread to begin reading input from the command line.
     */
    public InputReaderCLI() {
        this.start();
    }

    /**
     * The run method contains the main logic of the InputReaderCLI thread.
     * It continuously reads input from the command line and adds it to the buffer.
     */
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.add(temp);
        }
    }

    /**
     * Gets the buffer associated with this InputReaderCLI.
     *
     * @return The buffer from which input data can be read.
     */
    public Buffer getBuffer(){
        return buffer;
    }
}


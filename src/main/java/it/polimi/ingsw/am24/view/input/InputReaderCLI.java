package it.polimi.ingsw.am24.view.input;

import java.util.Scanner;

public class InputReaderCLI extends Thread implements InputReader {
    private final Buffer buffer = new Buffer();

    public InputReaderCLI() {
        this.start();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.add(temp);
        }
    }

    public Buffer getBuffer(){
        return buffer;
    }
}


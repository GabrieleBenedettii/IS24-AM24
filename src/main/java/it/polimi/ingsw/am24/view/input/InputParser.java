package it.polimi.ingsw.am24.view.input;

import it.polimi.ingsw.am24.view.Flow;

import java.rmi.RemoteException;

/**
 * The InputParser class extends the Thread class and processes input commands from a buffer.
 * It handles commands to send public and private messages and processes other data by adding it to another buffer.
 */
public class InputParser extends Thread {

    private final Buffer input;

    private final Buffer dataToProcess;

    private final Flow gameFlow;

    private Integer gameId;

    private String nickname;

    /**
     * Initializes a new instance of the InputParser class.
     *
     * @param input    The buffer from which input commands are read.
     * @param gameFlow The Flow instance used to handle game-related operations.
     */
    public InputParser(Buffer input, Flow gameFlow) {
        this.input = input;
        this.dataToProcess = new Buffer();
        this.gameFlow = gameFlow;
        this.nickname = "";
        this.start();
    }

    /**
     * The run method contains the main logic of the InputParser thread.
     * It continuously reads input from the buffer and processes commands for sending messages.
     */
    public void run() {
        String txt;
        while (!this.isInterrupted()) {

            //I keep popping data from the buffer sync
            //(so I wait myself if no data is available on the buffer)
            try {
                txt = input.pop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String[] substrings;
            //I popped an input from the buffer
            if (nickname != null && txt.startsWith("/cs ")) {
                substrings = txt.split(" ");
                String receiver = substrings[1];
                String message = txt.substring(receiver.length() + 4);
                try {
                    gameFlow.sendPrivateMessage(nickname, receiver, message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (nickname != null && txt.startsWith("/c ")) {
                //I send a message
                String message = txt.substring(3);
                try {
                    gameFlow.sendPublicMessage(nickname, message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                dataToProcess.add(txt);
            }
        }
    }

    /**
     * Sets the game ID for the parser.
     *
     * @param gameId The ID of the game.
     */
    public void setIdGame(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * Sets the nickname for the parser.
     *
     * @param nickname The nickname of the user.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets the buffer that stores data to be processed.
     *
     * @return The buffer containing data to be processed.
     */
    public Buffer getDataToProcess() {
        return dataToProcess;
    }
}

package it.polimi.ingsw.am24.view.input;

import it.polimi.ingsw.am24.view.Flow;

import java.rmi.RemoteException;

public class InputParser extends Thread {

    private final Buffer input;

    private final Buffer dataToProcess;

    private final Flow gameFlow;

    private Integer gameId;

    private String nickname;

    public InputParser(Buffer input, Flow gameFlow) {
        this.input = input;
        this.dataToProcess = new Buffer();
        this.gameFlow = gameFlow;
        this.nickname = "";
        this.start();
    }

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

    public void setIdGame(Integer gameId) {
        this.gameId = gameId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Buffer getDataToProcess() {
        return dataToProcess;
    }
}

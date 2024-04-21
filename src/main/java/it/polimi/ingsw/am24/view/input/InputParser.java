package it.polimi.ingsw.am24.view.input;

import it.polimi.ingsw.am24.view.GameFlow;

public class InputParser extends Thread {

    private final Buffer input;

    private final Buffer dataToProcess;

    private final GameFlow gameFlow;

    private Integer gameId;

    private String nickname;

    public InputParser(Buffer input, GameFlow gameFlow) {
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

            //I popped an input from the buffer
            /*if (nickname != null && txt.startsWith("/cs")) {
                txt = txt.charAt(3) == ' ' ? txt.substring(4) : txt.substring(3);
                if(txt.contains(" ")){
                    String receiver = txt.substring(0, txt.indexOf(" "));
                    String msg = txt.substring(receiver.length() + 1);
                    gameFlow.sendMessage(new MessagePrivate(msg, nickname, receiver));
                }
            } else if (nickname != null && txt.startsWith("/c")) {
                //I send a message
                txt = txt.charAt(2) == ' ' ? txt.substring(3) : txt.substring(2);
                gameFlow.sendMessage(new Message(txt, nickname));

            } else if (txt.startsWith("/quit") || (txt.startsWith("/leave"))) {
                assert nickname != null;
                System.exit(1);
                //gameFlow.leave(p.getNickname(), gameId);
                //gameFlow.youLeft();

            } else {*/
                //I didn't pop a message

                //I add the data to the buffer processed via GameFlow
                dataToProcess.add(txt);
            //}
        }
    }

    public void setIdGame(Integer gameId) {
        this.gameId = gameId;
    }

    public void setPlayer(String nickname) {
        this.nickname = nickname;
    }

    public Buffer getDataToProcess() {
        return dataToProcess;
    }
}

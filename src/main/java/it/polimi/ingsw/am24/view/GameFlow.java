package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.network.rmi.RMIClient;
import it.polimi.ingsw.am24.view.commandLine.CLI;
import it.polimi.ingsw.am24.view.flow.CommonClientActions;
import it.polimi.ingsw.am24.view.flow.Flow;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.flow.utility.Event;
import it.polimi.ingsw.am24.view.flow.utility.EventType;
import it.polimi.ingsw.am24.view.flow.utility.GameStatus;
import it.polimi.ingsw.am24.view.input.InputParser;
import it.polimi.ingsw.am24.view.input.InputReader;
import it.polimi.ingsw.am24.view.input.InputReaderCLI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.*;

public class GameFlow extends Flow implements Runnable, CommonClientActions {
    private String nickname;

    private CommonClientActions actions;

    private GameStatus status = GameStatus.BEGIN;

    private final Queue<Event> events = new LinkedList<>();

    private final UI ui;

    private boolean ended = false;

    protected InputParser inputParser;
    protected InputReader inputReader;

    private boolean joined;
    private ArrayList<String> availableColors;
    private ArrayList<GameCardView> cards;
    private GameView gameView;

    public GameFlow(String connectionType) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this);
            //case "SOCKET" -> actions = new SocketClient(this);
        }

        ui = new CLI();

        nickname = "";
        joined = false;
        this.inputReader = new InputReaderCLI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        Event event;
        addEvent(EventType.APP_MENU);

        while (!Thread.interrupted()) {
            if (joined) {
                //Get one event
                event = events.poll();
                if (event != null) {
                    //if something happened
                    switch (status) {
                        case BEGIN -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING, LAST_LAST_ROUND, LAST_ROUND -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> {
                            try {
                                statusEnded(event);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } else {
                event = events.poll();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
        }
    }

    private void statusNotInAGame(Event event) {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }
            case NICKNAME_ALREADY_USED -> {
                nickname = null;
                events.add(new Event(EventType.APP_MENU));
                //ui.addImportantEvent("WARNING> Nickname already used!");
            }
            case NO_LOBBY_AVAILABLE -> {
                nickname = null;
                events.add(new Event(EventType.APP_MENU));
            }
            /*case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Game is Full!");
            }*/
            /*case GENERIC_ERROR_WHEN_ENTRYING_GAME -> {
                nickname = null;
                ui.show_returnToMenuMsg();
                try {
                    this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, APP_MENU);
            }*/
        }
    }

    private void statusWait(Event event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case AVAILABLE_COLORS -> askColor();
            case HIDDEN_GOAL_CHOICE -> askSecretGoalDealt();
            case INITIAL_CARD_SIDE -> askInitialCardSide();
        }
    }

    private void statusRunning(Event event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case BEGIN_PLAY -> {
                this.inputParser.setPlayer(nickname);
                this.inputParser.setIdGame(gameView.getGameId());

                if(gameView.getCurrent().equals(nickname)) {
                    ui.show_gameView(gameView);
                    askCardPlay();
                }
                else
                    ui.show_current_player(gameView.getCurrent());
            }
            case BEGIN_DRAW -> {
                if(gameView.getCurrent().equals(nickname)) {
                    ui.show_table(gameView, true);
                    askCardDraw();
                }
                else
                    ui.show_current_player(gameView.getCurrent());
            }
            /*case SENT_MESSAGE -> {
                //todo fix
                ui.show_sent_message(nickname);
            }*/
        }
    }

    private void statusEnded(Event event) throws InterruptedException {
        switch (event.getType()) {
            case GAME_ENDED -> {
                //new Scanner(System.in).nextLine();
                this.inputParser.getDataToProcess().pop();
                try {
                    this.inputParser.getDataToProcess().pop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                /*this.leave(nickname, event.getModel().getGameId());
                this.youLeft();*/
            }
        }
    }

    public boolean isEnded() {
        return ended;
    }

    private void askNickname() {
        ui.show_insert_nickname();
        try {
            nickname = this.inputParser.getDataToProcess().pop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //ui.show_chosenNickname(nickname);
    }

    private boolean askSelectGame() {
        String choice;
        ended = false;
        ui.show_lobby();
        try {
            choice = this.inputParser.getDataToProcess().pop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        askNickname();

        switch (choice) {
            case "c" -> {
                Integer numPlayers = askNumPlayers();
                if(numPlayers < 2 || numPlayers > 4) return false;
                createGame(nickname, numPlayers);
            }
            case "j" -> joinFirstGameAvailable(nickname);
            /*case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }*/
            default -> {
                return false;
            }
        }

        return true;
    }

    private void askColor() {
        String color;
        ui.show_available_colors(availableColors);
        try {
            color = this.inputParser.getDataToProcess().pop();
            chooseColor(nickname, color);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer askNumPlayers() {
        String temp;
        Integer numPlayers = null;
        do {
            //ui.show_inputGameIdMsg();
            System.out.print("\nInsert number of players -> ");
            try {
                try {
                    temp = this.inputParser.getDataToProcess().pop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                numPlayers = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("Nan");
                //ui.show_NaNMsg();
            }
        } while (numPlayers == null);
        return numPlayers;
    }

    private void askSecretGoalDealt() {
        ui.show_hidden_goal(cards);
        try {
            int choice = Integer.parseInt(this.inputParser.getDataToProcess().pop());
            actions.chooseHiddenGoal(nickname, cards.get(choice).getCardId());
        } catch (Exception e) {
            System.out.println("Nan");
        }
    }

    private void askInitialCardSide() {
        ui.show_initial_side(cards);
        try {
            int choice = Integer.parseInt(this.inputParser.getDataToProcess().pop());
            actions.chooseInitialCardSide(nickname, choice);
        } catch (Exception e) {
            System.out.println("Nan");
        }
    }

    private void askCardPlay() throws IOException {
        String command = "";
        //ui.show_player_view();
        do {
            try {
                command = this.inputParser.getDataToProcess().pop();
            } catch (Exception e) {
                System.out.println("Wrong command");
            }
            String[] command_args = command.split(" ");

            if(command_args[0].equals("play")) {
                if (!command_args[2].equals("front") && !command_args[2].equals("back")) continue;
                boolean front = command_args[2].equals("front");

                int x;
                int y;
                int cardIndex;
                try {
                    cardIndex = Integer.parseInt(command_args[1]);
                    x = Integer.parseInt(command_args[3]);
                    y = Integer.parseInt(command_args[4]);
                } catch (Exception e) {
                    System.out.println("Nan");
                    continue;
                }

                if (!gameView.getPlayerView().getPossiblePlacements()[x][y])
                    continue;

                playCard(nickname,cardIndex,front,x,y);
            }
        } while(false);
    }

    private void askCardDraw() {
        String command = "";
        //ui.show_public_board_view();
        do {
            try {
                command = this.inputParser.getDataToProcess().pop();
            } catch (Exception e) {
                System.out.println("Wrong command");
            }
            String[] command_args = command.split(" ");
            if(command_args[0].equals("draw")) {
                try {
                    int cardIndex = Integer.parseInt(command_args[1]);
                    if(cardIndex < 0 || cardIndex > 5) continue;

                    drawCard(nickname, cardIndex);
                }
                catch (Exception e) {
                    System.out.println("Wrong command!");
                }
            }
        }while(false);
    }

    //METHODS THAT THE CLIENT CAN REQUEST TO THE SERVER

    @Override
    public void createGame(String nickname, int numPlayers) {
        //ui.show_creatingNewGameMsg(nickname);
        try {
            actions.createGame(nickname, numPlayers);
        } catch (IOException | NotBoundException e) {
            System.out.println("Error during creating game");
        }
    }

    @Override
    public void joinFirstGameAvailable(String nickname) {
        //ui.show_joiningFirstAvailableMsg(nickname);
        try {
            actions.joinFirstGameAvailable(nickname);
        } catch (IOException | NotBoundException e) {
            System.out.println("Error during joining game");
        }
    }

    @Override
    public void chooseColor(String nickname, String color) throws IOException {
        actions.chooseColor(nickname, color);
    }

    @Override
    public void chooseHiddenGoal(String nickname, int choice) throws IOException {
        actions.chooseHiddenGoal(nickname, choice);
    }

    @Override
    public void chooseInitialCardSide(String nickname, int choice) throws IOException {
        actions.chooseInitialCardSide(nickname, choice);
    }

    @Override
    public void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException {
        actions.playCard(nickname, cardIndex, front, x, y);
    }

    @Override
    public void drawCard(String nickname, int choice) throws IOException {
        actions.drawCard(nickname, choice);
    }

    /*@Override
    public void sendMessage(ChatMessage msg) {
        try {
            actions.sendMessage(msg);
        } catch (RemoteException e) {
            System.out.println("Error sending message");
        }
    }*/

    //EVENTS RECEIVED FROM THE SERVER

    @Override
    public void invalidNumPlayers() {
        addEvent(EventType.APP_MENU);
    }

    @Override
    public void playerJoined(ArrayList<String> players) {
        ui.show_joined_players(players);
    }

    @Override
    public void nicknameAlreadyUsed() {
        events.add(new Event(EventType.NICKNAME_ALREADY_USED));
    }

    @Override
    public void availableColors(ArrayList<String> colors) {
        this.availableColors = colors;
        this.status = GameStatus.BEGIN;
        addEvent(EventType.AVAILABLE_COLORS);
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews) {
        addEvent(EventType.HIDDEN_GOAL_CHOICE);
        this.cards = cardViews;
    }

    @Override
    public void initialCardSide(GameCardView front, GameCardView back) {
        addEvent(EventType.INITIAL_CARD_SIDE);
        this.cards = new ArrayList<>();
        cards.add(front);
        cards.add(back);
    }

    @Override
    public void beginTurn(GameView gameView) {
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_PLAY);
        this.gameView = gameView;
    }

    @Override
    public void beginDraw() {
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_DRAW);
    }

    @Override
    public void wrongCardPlay(GameView gameView) {
        addEvent(EventType.BEGIN_PLAY);
        this.gameView = gameView;
        ui.show_wrong_card_play();
    }

    /*@Override
    public void sentMessage(ChatMessage msg) {
        //Show the message only if is for everyone or is for me (or I sent it)
        if (msg.getReceiver().equals("") || msg.getReceiver().equals(nickname) || msg.getSender().equals(nickname)) {
            ui.add_message(msg.getSender(), msg.getMessage(), msg.getTime());
            addEvent(EventType.SENT_MESSAGE);
        }
    }*/

    @Override
    public void gameEnded(String winner, HashMap<String,Integer> rank) {
        ended = true;
        addEvent(EventType.GAME_ENDED);
        ui.show_winner_and_rank(winner.equals(nickname), rank);
        //resetGameId(fileDisconnection, gameModel);
    }

    private void addEvent(EventType type) {
        events.add(new Event(type));
        if (type.equals(EventType.AVAILABLE_COLORS) || type.equals(EventType.PLAYER_JOINED) || (status != null && (status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_LAST_ROUND) || status.equals(GameStatus.LAST_ROUND)) ))
            joined = true;

        if(type.equals(EventType.APP_MENU))
            joined = false;
    }
}

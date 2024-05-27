package it.polimi.ingsw.am24.heartbeat;

public class HeartBeat {
    private final Long beat;
    private final String nickname;

    public HeartBeat(Long beat, String nickname) {
        this.beat = beat;
        this.nickname = nickname;
    }

    public Long getBeat() {
        return beat;
    }

    public String getNickname() {
        return nickname;
    }
}

package it.polimi.ingsw.am24.heartbeat;

/**
 * The HeartBeat class represents the heartbeat information for a player.
 * It stores the timestamp of the last heartbeat and the nickname of the player.
 */
public class HeartBeat {
    private final Long beat;
    private final String nickname;

    /**
     * Constructs a HeartBeat object with the provided timestamp and nickname.
     * @param beat The timestamp indicating the time of the last heartbeat.
     * @param nickname The nickname of the player associated with the heartbeat.
     */
    public HeartBeat(Long beat, String nickname) {
        this.beat = beat;
        this.nickname = nickname;
    }

    /**
     * Retrieves the timestamp of the last heartbeat.
     * @return The timestamp representing the time of the last heartbeat.
     */
    public Long getBeat() {
        return beat;
    }

    /**
     * Retrieves the nickname of the player associated with the heartbeat.
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }
}

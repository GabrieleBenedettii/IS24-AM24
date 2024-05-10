package it.polimi.ingsw.am24.view.flow.utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

import it.polimi.ingsw.am24.constants.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DisconnectionFile {
    private final String path;
    //init class
    public DisconnectionFile() {
        this.path = System.getProperty("user.home") + "/appData/Roaming/.CodexNaturalis";
    }

    public int getLastGameId(String nickname) {
        String gameId = null;
        String time = null;
        JSONParser parser = new JSONParser();
        File file = new File(path + "/" + nickname + ".json");

        try (InputStream is = new FileInputStream(file);
             Reader reader = new InputStreamReader(Objects.requireNonNull(is, "Couldn't find json file"), StandardCharsets.UTF_8)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            gameId = (String) obj.get(Constants.gameIdData);
            time = (String) obj.get(Constants.gameIdTime);
        } catch (ParseException | IOException ex) {
            return -1;
        }
        assert gameId != null;
        if (LocalDateTime.parse(time).isBefore(LocalDateTime.now().plusSeconds(Constants.offSet)))
            return Integer.parseInt(gameId);
        else
            return -1;
    }

    public void setLastGameId(String nickname, int gameId) {
        JSONObject data = new JSONObject();
        data.put(Constants.gameIdData, Integer.toString(gameId));
        data.put(Constants.gameIdTime, LocalDateTime.now().toString());
            //creates a directory if not present
        new File(path).mkdirs();

        File file = new File(path + "/" + nickname + ".json");
        try {
            //creates a file if not present
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(path + "/" + nickname + ".json")) {
            fileWriter.write(data.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

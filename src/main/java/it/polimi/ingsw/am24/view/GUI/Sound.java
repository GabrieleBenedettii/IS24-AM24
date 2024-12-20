package it.polimi.ingsw.am24.view.GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Provides methods for playing audio sounds in a Java application.
 */
public class Sound {
    public static boolean play = true;

    /**
     * Plays a specified sound file asynchronously.
     *
     * @param Name The name of the sound file to be played.
     */
    public static synchronized void playSound(final String Name) {
        if (play) {
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();

                    InputStream in = Sound.class.getResourceAsStream("/it/polimi/ingsw/am24/sounds/" + Name);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while (true) {
                        assert in != null;
                        if ((bytesRead = in.read(buffer)) == -1) break;
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] audioData = outputStream.toByteArray();

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);

                    clip.open(inputStream);
                    FloatControl gainControl =
                            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-10.0f);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }).start();
        }
    }
}

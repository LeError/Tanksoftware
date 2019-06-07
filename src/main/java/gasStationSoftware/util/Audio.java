package gasStationSoftware.util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;

public class Audio {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Audiodatei abspielen
     * @param audio Path of the audio file.
     * @exception Exception
     * @author Robin Herder
     */
    public void play(BufferedInputStream audio) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();
            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }
            audioLine.drain();
            audioLine.close();
            audioStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

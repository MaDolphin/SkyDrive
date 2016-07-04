package com.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.io.File;

public class MyPlayer {

    private static final int BUFFER_SIZE = 176400; // 44100 x 16 x 2 / 8

    public void StartPlayer(String filePath) throws Exception {

        byte[]  buffer = new byte[BUFFER_SIZE];
        File soundFile = new File(filePath);
        AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(soundFile));
        AudioFormat audioFormat = in.getFormat();

        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
        line.open(audioFormat);
        line.start();

        while (true) {
            int n = in.read(buffer, 0, buffer.length);
            if (n < 0) {
                break;
            }
            line.write(buffer, 0, n);
        }
        line.drain();
        line.close();
    }
}

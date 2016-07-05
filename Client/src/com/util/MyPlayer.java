package com.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.io.File;

public class MyPlayer extends Thread {

    private static final int BUFFER_SIZE = 176400; // 44100 x 16 x 2 / 8

    private String filePath = null;

    Runnable1 r = new Runnable1();
    Thread t = new Thread(r);

    public void StartPlayer(String filePath) throws Exception {

        this.filePath = filePath;
        t.start();

    }

    public void StopThread(){

        t.stop();

    }

    class Runnable1 implements Runnable{
        public void run() {

            byte[]  buffer = new byte[BUFFER_SIZE];
            File soundFile = new File(filePath);
            try {
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

package org.geometerplus.android.fbreader.tts;

public interface IPlayer {

    int getDuration();

    int getCurrentPosition();

    void start();

    void pause();

    void stop();

    String getSpeed();

    void setSpeed(String speed);
}
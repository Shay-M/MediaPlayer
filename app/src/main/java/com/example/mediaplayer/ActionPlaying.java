package com.example.mediaplayer;/* Created by Shay Mualem 25/07/2021 */

public interface ActionPlaying {
    void nextClicked();

    void prevClicked();

    void playClicked();

    void addClicked(String stringUrl) throws Exception;
}

package com.benjaminspeakman.noahfence;

import android.util.Log;

public class Joke {
    int id;
    String setup, delivery, joke;

    public int getId() {
        return id;
    }

    public String getText() {
        if (joke != null) return joke;

        return setup + '\n' + delivery;
    }
}

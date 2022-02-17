package com.benjaminspeakman.noahfence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class JokeApiResponse {

    List<Joke> jokes;

    public JokeApiResponse() {
        jokes = new ArrayList<Joke>();
    }

    public List<Joke> getJokeList() {
        return jokes;
    }

    public static JokeApiResponse parseJson(String resp) {
        Gson gson = new GsonBuilder().create();
        JokeApiResponse jokeApiResponse = gson
                .fromJson(resp, JokeApiResponse.class);
        return jokeApiResponse;
    }
}

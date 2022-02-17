package com.benjaminspeakman.noahfence;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeApiService {
    @GET("Any?amount=2")
    Call<com.benjaminspeakman.noahfence.JokeApiResponse> getAnyJoke();
}

package com.benjaminspeakman.noahfence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://v2.jokeapi.dev/joke/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getJokeClicked(View view) {
        Log.w(getLocalClassName(), "getJokeClicked");

        //OkHttpClient client = new OkHttpClient();
        /*
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
           @Override
           public void intercept(RequestFacade request) {
               request.addQueryParam("apikey", apiKey);
           }
        };
         */
        //client.interceptors().add(reqInt)
        Retrofit retrofit = new Retrofit.Builder()
                //.client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokeApiService service = retrofit.create(JokeApiService.class);
        Call<JokeApiResponse> call = service.getAnyJoke();
        call.enqueue(new Callback<JokeApiResponse>() {
            @Override
            public void onResponse(Call<JokeApiResponse> call, Response<JokeApiResponse> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    List<Joke> jokeList = resp.body().getJokeList();

                    if (jokeList != null && jokeList.size() > 0) {
                        for (int i = 0; i < jokeList.size(); i++) {
                            String txt = jokeList.get(i).getText();
                            Log.w(getLocalClassName(), i + ": " + txt);
                            if (i == 0) ((TextView) findViewById(R.id.joke_text)).setText(txt);
                        }
                    } else {
                        Log.w(getLocalClassName(), "joke list null");
                    }
                } else {
                    Log.w(getLocalClassName(), "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<JokeApiResponse> call, Throwable t) {
                Log.e(getLocalClassName(), t.toString());
            }
        });
    }
}

/*
{
    "error": false,
    "amount": 2,
    "jokes": [
        {
            "category": "Misc",
            "type": "twopart",
            "setup": "How do construction workers party?",
            "delivery": "They raise the roof.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 217,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Programming",
            "type": "single",
            "joke": "A SQL statement walks into a bar and sees two tables.\nIt approaches, and asks \"may I join you?\"",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 5,
            "safe": true,
            "lang": "en"
        }
    ]
}
 */

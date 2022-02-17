package com.benjaminspeakman.noahfence;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@LargeTest
public class MainActivityTest {
    private MockWebServer server;

    @Before
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start(8080);

        File file = new File("jokeApiResponse.json");
        FileInputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        String mockJson = sb.toString();

        MockResponse mockResp = new MockResponse()
                .setResponseCode(200)
                .setBody(mockJson);
        server.enqueue(mockResp);
    }

    @After
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void mainActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.joke_text),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Hello World!")));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_get_joke), withText("Get Joke"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.joke_text),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("mock response")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

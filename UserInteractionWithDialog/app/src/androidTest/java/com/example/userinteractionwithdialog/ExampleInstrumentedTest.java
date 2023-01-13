package com.example.userinteractionwithdialog;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.userinteractionwithdialog", appContext.getPackageName());
    }

    @Test
    public void intentTest()
    {
        onView(withId(R.id.et_nama)).perform(typeText("Bunga Lestari"), closeSoftKeyboard());
        onView(withId(R.id.et_nama)).check(matches(withText("Bunga Lestari")));
        onView(withId(R.id.rb_perempuan)).perform(click());
        onView(withId(R.id.et_alamat)).perform(typeText("Jl. Sudirman No 123"), pressImeActionButton());
        onView(withId(R.id.et_notel)).perform(typeText("+6278912345678"), pressImeActionButton());
        onView(withId(R.id.et_email)).perform(typeText("Bunga@gmail.com"), pressImeActionButton());
        onView(withId(R.id.et_password)).perform(typeText("123456"), pressImeActionButton());
        onView(withId(R.id.spin_jurusan)).perform(click());
        onData(is("IPS")).perform(click());

        onView(withId(R.id.btn_daftar)).perform(click());
        intending(hasExtraWithKey("NAMA"));

        Intent receivedIntent = Iterables.getOnlyElement(Intents.getIntents());
        assertEquals(receivedIntent.getExtras().getString("NAMA"),"Bunga Lestari");
    }
}
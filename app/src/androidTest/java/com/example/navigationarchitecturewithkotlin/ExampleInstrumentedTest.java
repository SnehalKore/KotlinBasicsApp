package com.example.navigationarchitecturewithkotlin;

import android.content.Context;
import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.navigationarchitecturewithkotlin.views.activity.MainActivity;
import com.example.navigationarchitecturewithkotlin.views.activity.SplashActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.navigationarchitecturewithkotlin", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<SplashActivity> activityActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }


}

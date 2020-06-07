package com.example.navigationarchitecturewithkotlin;


import com.example.navigationarchitecturewithkotlin.views.activity.MainActivity;
import com.example.navigationarchitecturewithkotlin.views.activity.SplashActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertNotNull;

@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class)
public class ExampleUnitTest {

    private SplashActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(SplashActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }
    @Test
    public void shouldHaveSplashFragment() throws Exception
    {
        assertNotNull( activity.getFragmentManager().findFragmentById(R.id.splashFragment) );
    }
}
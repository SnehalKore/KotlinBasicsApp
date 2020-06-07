package com.example.navigationarchitecturewithkotlin;

import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.navigationarchitecturewithkotlin.views.activity.MainActivity;
import com.example.navigationarchitecturewithkotlin.views.fragments.DataFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doReturn;

@RunWith(AndroidJUnit4.class)
public class TestMainActivity {
    public final TestComponentRule componentRule = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final MyTestRule<MainActivity> myActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);

    @Before
    public void setup() {
        doReturn(Observable.just(MockModelFabric.newSettings()))
                .when(componentRule.getMockDataManager())
                .getSettings();

        myActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
    }

    @Test
    public void coolTextDisplayed() {
        onView(withId(R.id.txt_cool_message)).check(matches(withText(“So Cool!)));
    }

    @Test
    public void checkTextDisplayedInDynamicallyCreatedFragment() {
        DataFragment fragment = new DataFragment();
        myActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_controller_view_tag, fragment).commit();

        onView(withId(R.id.txt_cool_message)).check(matches(withText("Cool")));
    }

}



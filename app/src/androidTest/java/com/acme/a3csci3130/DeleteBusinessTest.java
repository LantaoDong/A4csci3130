package com.acme.a3csci3130;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.firebase.ui.database.FirebaseListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.anything;

/**
 * Created by lantaodong on 2018-03-12.
 */

public class DeleteBusinessTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void DeleteBusinessTest() throws Exception {
        FirebaseListAdapter<Contact> adapter = activityRule.getActivity().getAdapter();
        int newCount = adapter.getCount()-1;

        //click First row -> click DELETE -> check row number

        //wait 5 seconds to connect database
        TimeUnit.SECONDS.sleep(5);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(adapter.getCount()-1).perform(click());

        onView(withId(R.id.deleteButton)).perform(click());


        //check if record deleted successfully
        assertEquals(newCount, adapter.getCount());
    }
}

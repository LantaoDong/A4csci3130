package com.acme.a3csci3130;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.firebase.ui.database.FirebaseListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.anything;

/**
 * Created by lantaodong on 2018-03-12.
 */

public class UpdateBusinessTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testUpdateBusiness() throws Exception {
        FirebaseListAdapter<Contact> adapter = mActivityRule.getActivity().getAdapter();
        //click First row -> change fields -> click UPDATE DATA

        //wait 3 seconds to connect database
        TimeUnit.SECONDS.sleep(3);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(adapter.getCount()-1).perform(click());

        //new business number
        String newBNum = "999999999";

        onView(withId(R.id.businessNumberDetail)).perform(clearText());
        onView(withId(R.id.businessNumberDetail)).perform(typeText(newBNum));
        onView(withId(R.id.businessNumberDetail)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.updateButton)).perform(click());


        //check if values are updated correctly/successfully
        Log.e("business number: ", adapter.getItem(adapter.getCount()-1).businessNumber);
        assertEquals(newBNum, adapter.getItem(adapter.getCount()-1).businessNumber);
    }
}

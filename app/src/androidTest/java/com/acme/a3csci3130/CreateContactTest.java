package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.firebase.ui.database.FirebaseListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CreateContactTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    //test if createContact page words fine
    @Test
    public void createContactTest() throws Exception{
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.businessNumber)).perform(typeText("123456789"));
        onView(withId(R.id.name)).perform(typeText("Lantao Dong"));
        onView(withId(R.id.primaryBusiness)).perform(typeText("Fisher"));
        onView(withId(R.id.address)).perform(typeText("Quinpool"));
        onView(withId(R.id.province)).perform(typeText("NS"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submitButton)).perform(click());

        FirebaseListAdapter<Contact> adapter = activityRule.getActivity().getAdapter();

        //check if input data is pushed into database successfully
        assertEquals("123456789",adapter.getItem(adapter.getCount()-1).businessNumber);
        assertEquals("Lantao Dong",adapter.getItem(adapter.getCount()-1).name);
        assertEquals("Fisher",adapter.getItem(adapter.getCount()-1).primaryBusiness);
        assertEquals("Quinpool",adapter.getItem(adapter.getCount()-1).address);
        assertEquals("NS",adapter.getItem(adapter.getCount()-1).province);
    }
}

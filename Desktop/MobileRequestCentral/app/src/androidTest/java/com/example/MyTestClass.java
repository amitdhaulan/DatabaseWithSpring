package com.example;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilerequestcentral.LogIn;
import com.example.mobilerequestcentral.R;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by amitk on 10/27/2015.
 */
public class MyTestClass  extends ActivityInstrumentationTestCase2<LogIn> {

    private LogIn mActivity;

    public MyTestClass() {
        super(LogIn.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();

        testChangeText_sameActivity();
    }

    public void testChangeText_sameActivity() {
        // Type text and then press the button.
        /*onView(withId(R.id.userid_et))
                .perform(typeText("amit"), closeSoftKeyboard());

        onView(withId(R.id.password_et))
                .perform(typeText("password"), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());*/


        TextView name  =
                (TextView) mActivity.findViewById(R.id.userid_et);
        String nameRes = name.getText().toString();

        TextView pass=
                (TextView) mActivity.findViewById(R.id.password_et);
        String passRes = pass.getText().toString();

        if(nameRes.equals("prasann") && passRes.equals("password")){
            /*onView(withId(R.id.login_button)).perform(click());*/
            Button login =
                    (Button) mActivity.findViewById(R.id.login_button);
            login.performClick();
        }else{
            mActivity.finish();
            assertEquals("Pushpendra", nameRes);

        }




    }



}
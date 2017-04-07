package pl.rozen.swim.BMICalculator;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;
import java.util.Random;

import pl.rozen.swim.BMICalculator.activities.MyActivity;
import pl.rozen.swim.BMICalculator.interfaces.ICountBMI;
import pl.rozen.swim.BMICalculator.utils.CountBMIForImperials;
import pl.rozen.swim.BMICalculator.utils.CountBMIForMetrics;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {


    @Rule
    public ActivityTestRule<MyActivity> mActivityRule =
            new ActivityTestRule<>(MyActivity.class);

    @Test
    public void unitsChangesProperly(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()){
                onView(withId(R.id.metric_units_RadioButton)).perform(click());

                onView(withId(R.id.height_unit_TextView)).check(matches(withText(R.string.height_metric_unit)));
                onView(withId(R.id.mass_unit_TextView)).check(matches(withText(R.string.mass_metric_unit)));
            }
            else {
                onView(withId(R.id.imperial_units_RadioButton)).perform(click());

                onView(withId(R.id.height_unit_TextView)).check(matches(withText(R.string.height_imperial_unit)));
                onView(withId(R.id.mass_unit_TextView)).check(matches(withText(R.string.mass_imperial_unit)));
            }
        }
    }

    @Test
    public void properWarningsDisplayed(){
        Random random = new Random();
        ICountBMI metricBMI = new CountBMIForMetrics();
        ICountBMI imperialBMI = new CountBMIForImperials();
        for (int i = 0; i < 10; i++) {

            if (i%2 == 0){ //metric option
                onView(withId(R.id.metric_units_RadioButton)).perform(click());

                float toInputMass = random.nextFloat()*1000 +1;
                while (metricBMI.isValidMass(toInputMass))
                    toInputMass = random.nextFloat()*1000 +1;

                float toInputHeight = random.nextFloat()*1000 +1;
                while (metricBMI.isValidMass(toInputHeight))
                    toInputHeight = random.nextFloat()*1000 +1;

                onView(withId(R.id.mass_EditText))
                        .perform(typeText(String.format(new Locale("en", "EN"), "%.2f", toInputMass)), closeSoftKeyboard());
                onView(withId(R.id.height_EditText))
                        .perform(typeText(String.format(new Locale("en", "EN"), "%.2f", toInputHeight)), closeSoftKeyboard());

                onView(withId(R.id.calc_bmi_Button)).perform(click());


                onView(withId(R.id.mass_EditText)).check(matches(hasErrorText("Wrong mass")));
                onView(withId(R.id.height_EditText)).check(matches(hasErrorText("Wrong height")));

            }
            else { // imperial option
                onView(withId(R.id.imperial_units_RadioButton)).perform(click());

                float toInputMass = random.nextFloat()*1000 +1;
                while (imperialBMI.isValidMass(toInputMass))
                    toInputMass = random.nextFloat()*10000 +1;

                float toInputHeight = random.nextFloat()*1000 +1;
                while (imperialBMI.isValidMass(toInputHeight))
                    toInputHeight = random.nextFloat()*1000 +1;

                onView(withId(R.id.mass_EditText))
                        .perform(typeText(String.format(new Locale("en", "EN"), "%.1f", toInputMass)), closeSoftKeyboard());
                onView(withId(R.id.height_EditText))
                        .perform(typeText(String.format(new Locale("en", "EN"), "%.1f", toInputHeight)), closeSoftKeyboard());

                onView(withId(R.id.calc_bmi_Button)).perform(click());

                onView(withId(R.id.mass_EditText)).check(matches(hasErrorText("Wrong mass")));
                onView(withId(R.id.height_EditText)).check(matches(hasErrorText("Wrong height")));
            }
        }
    }

    @Test
    public void ensureResultInvisibleWhenWrongInput() {
        // Type text and then press the button.
        onView(withId(R.id.mass_EditText))
                .perform(typeText("2213.23"), closeSoftKeyboard());
        onView(withId(R.id.height_EditText))
                .perform(typeText("993.23"), closeSoftKeyboard());
        onView(withId(R.id.calc_bmi_Button)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.result_TextView)).check(matches( withEffectiveVisibility(ViewMatchers.Visibility.GONE)));


        onView(withId(R.id.mass_EditText))
                .perform(typeText("999.3"), closeSoftKeyboard());
        onView(withId(R.id.height_EditText))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.calc_bmi_Button)).perform(click());

        onView(withId(R.id.result_TextView)).check(matches( withEffectiveVisibility(ViewMatchers.Visibility.GONE)));


        onView(withId(R.id.mass_EditText))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.height_EditText))
                .perform(typeText("12.23"), closeSoftKeyboard());
        onView(withId(R.id.calc_bmi_Button)).perform(click());

        onView(withId(R.id.result_TextView)).check(matches( withEffectiveVisibility(ViewMatchers.Visibility.GONE)));


        onView(withId(R.id.mass_EditText))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.height_EditText))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.calc_bmi_Button)).perform(click());

        onView(withId(R.id.result_TextView)).check(matches( withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }
}
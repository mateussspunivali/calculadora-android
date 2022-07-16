package br.com.forumtematico.calculadora;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Rule;
import org.junit.Test;


public class CalculadoraTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateSumNumbers() {
        onView(withId(R.id.button_one))
                .perform(click());

        onView(withId(R.id.button_addition))
                .perform(click());

        onView(withId(R.id.button_two))
                .perform(click());

        onView(withId(R.id.button_equal))
                .perform(click());


        onView(withId(R.id.textView_input_numbers))
                .check(matches(withText("3")));

        onView(withId(R.id.textView_history))
                .check(matches(withText("1+2")));
    }

    @Test
    public void validateSubtractNumbers() {
        onView(withId(R.id.button_five))
                .perform(click());

        onView(withId(R.id.button_subtraction))
                .perform(click());

        onView(withId(R.id.button_three))
                .perform(click());

        onView(withId(R.id.button_equal))
                .perform(click());


        onView(withId(R.id.textView_input_numbers))
                .check(matches(withText("2")));

        onView(withId(R.id.textView_history))
                .check(matches(withText("5-3")));
    }
}

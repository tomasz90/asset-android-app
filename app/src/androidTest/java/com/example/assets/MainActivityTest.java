package com.example.assets;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.assets.activities.activities.MainActivity;
import com.example.assets.storage.room.AssetDataBase;
import com.example.assets.storage.room.entity.Asset;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.assets.constants.AssetConstants.CURRENCIES;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void prepareTestData() {
        Application application = mainActivityActivityTestRule.getActivity().getApplication();
        AssetDataBase dataBase = AssetDataBase.getInstance(application);
        Asset testAsset = new Asset("PLN", CURRENCIES, 150f, "info");
        dataBase.assetDao().insert(testAsset);
    }

    @Test
    public void should_open_asset_type_list_when_clicking_add_asset_button() {
        // given
        onView(withId(R.id.fab))
        // when
        .perform(click());
        // then
        onView(withId(R.id.generic_list))
        .check(matches(isDisplayed()));
    }

    @Test
    @Ignore("isEnabled not working...")
    public void should_not_be_able_to_click_remove_assets_when_no_assets() {
        // given
        openActionBarOverflowOrOptionsMenu(mainActivityActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.remove_all_assets)))

        // then
        .check(matches(not(isEnabled())));
    }

    @Test
    public void should_see_empty_list_when_remove_all_assets_was_clicked() {
        // given
        RecyclerView recyclerView = mainActivityActivityTestRule.getActivity().findViewById(R.id.asset_list);
        openActionBarOverflowOrOptionsMenu(mainActivityActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.remove_all_assets))).perform(click());
        onView(withText(getString(R.string.want_to_remove_all_assets))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.yes))).perform(click());

        // then
        int expected = 0;
        int actual = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();
        assertThat(actual, equalTo(expected));
    }

    private String getString(int stringResource) {
        return mainActivityActivityTestRule.getActivity().getString(stringResource);
    }
}

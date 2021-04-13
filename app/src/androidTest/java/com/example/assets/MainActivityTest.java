package com.example.assets;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.azimolabs.conditionwatcher.ConditionWatcher;
import com.example.assets.activity.MainActivity;
import com.example.assets.activity.list_adapters.AssetDetailsAdapter;
import com.example.assets.storage.room.AssetDataBase;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    private Asset testAsset = new Asset("PLN", "CURRENCIES", 150f, "info");
    private BaseCurrency baseCurrency = new BaseCurrency("USD");
    private Condition.LoadingListItems listLoaded;
    private AssetDataBase dataBase;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void prepareForTest() throws Exception {
        Application application = mainActivityTestRule.getActivity().getApplication();
        dataBase = AssetDataBase.getInstance(application);
        dataBase.assetDao().insert(testAsset);
        dataBase.baseCurrencyDao().update(baseCurrency);

        RecyclerView recyclerView = mainActivityTestRule.getActivity().findViewById(R.id.asset_list);
        listLoaded = new Condition.LoadingListItems(recyclerView);
        ConditionWatcher.waitForCondition(listLoaded);
    }

    @After
    public void closeTest() {
        Application application = mainActivityTestRule.getActivity().getApplication();
        dataBase = AssetDataBase.getInstance(application);
        dataBase.assetDao().deleteAll();
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
    public void should_see_empty_list_when_removing_all_assets() {
        // given
        openActionBarOverflowOrOptionsMenu(mainActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.remove_all_assets))).perform(click());
        onView(withText(getString(R.string.want_to_remove_all_assets))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.yes))).perform(click());

        // then
        expectTestDataRemoved();
    }

    @Test
    public void should_not_see_empty_list_when_removing_all_assets_but_not_confirming() {
        // given
        openActionBarOverflowOrOptionsMenu(mainActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.remove_all_assets))).perform(click());
        onView(withText(getString(R.string.want_to_remove_all_assets))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.no))).perform(click());

        // then
        expectTestDataNotRemoved();
    }

    @Test
    public void should_not_see_asset_when_removing_it() {
        // when
        onView(withText(testAsset.getSymbol())).perform(ViewActions.swipeRight());
        onView(withText(getString(R.string.want_to_remove_asset))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.yes))).perform(click());

        // then
        expectTestDataRemoved();
    }

    @Test
    public void should_see_asset_when_removing_it_but_not_confirming()  {
        // when
        onView(withText(testAsset.getSymbol())).perform(ViewActions.swipeRight());
        onView(withText(getString(R.string.want_to_remove_asset))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.no))).perform(click());

        // then
        expectTestDataNotRemoved();
    }

    @Test
    public void should_asset_has_new_value_when_asset_was_edited() throws Exception {
        // given
        String expectedQuote = "1234";

        // when
        onView(withText(testAsset.getSymbol())).perform(ViewActions.swipeLeft());
        onView(withId(R.id.amount_input))
                .perform(clearText())
                .perform(typeText(expectedQuote));
        onView(withId(R.id.fab))
                .perform(click());

        // then
        ConditionWatcher.waitForCondition(new Condition.LoadingMainActivity());
        RecyclerView recyclerView = mainActivityTestRule.getActivity().findViewById(R.id.asset_list);
        ConditionWatcher.waitForCondition(listLoaded);
        int actualQuote = (int)((AssetDetailsAdapter) recyclerView.getAdapter()).getAssetAtPosition(0).getQuantity();
        assertEquals(Integer.parseInt(expectedQuote), actualQuote);

    }

    @Test
    public void should_not_be_able_to_remove_assets_when_no_assets() {
        // given
        dataBase.assetDao().deleteAll();
        openActionBarOverflowOrOptionsMenu(mainActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.remove_all_assets)))
                .perform(click())

        // then
        .check(matches(isDisplayed()));
    }

    @Test
    public void should_display_different_currency_than_usd_when_currency_was_changed() {
        // given
        String newCurrency = "EUR";
        openActionBarOverflowOrOptionsMenu(mainActivityTestRule.getActivity());

        // when
        onView(withText(getString(R.string.choose_base_currency)))
                .perform(click());
        onView(withText(newCurrency))
                .perform(click());

        // then
        onView(withId(R.id.total_value)).check(matches(withSubstring(newCurrency)));
    }

    @Test
    public void should_display_some_total_value_when_have_any_asset() {
        // then
        onView(withId(R.id.total_value)).check(matches(not(withText("0 " + baseCurrency.getSymbol()))));
    }

    private void expectTestDataNotRemoved() {
        RecyclerView recyclerView = mainActivityTestRule.getActivity().findViewById(R.id.asset_list);
        Asset asset = ((AssetDetailsAdapter) recyclerView.getAdapter()).getAssetAtPosition(0);
        String symbol = asset.getSymbol();
        int quantity = (int) asset.getQuantity();
        assertEquals(testAsset.getSymbol(), symbol);
        assertEquals((int) testAsset.getQuantity(), quantity);
    }

    private void expectTestDataRemoved() {
        RecyclerView recyclerView = mainActivityTestRule.getActivity().findViewById(R.id.asset_list);
        int actual = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();
        assertEquals(0, actual);
    }

    private String getString(int stringResource) {
        return mainActivityTestRule.getActivity().getString(stringResource);
    }
}

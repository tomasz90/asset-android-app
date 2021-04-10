package com.example.assets.integration;

import com.example.assets.util.AssetServices;
import com.example.assets.util.RateFacade;
import com.example.assets.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.assets.constants.AssetConstants.ALL_CRYPTOS;
import static com.example.assets.constants.AssetConstants.ALL_CURRENCIES;
import static com.example.assets.constants.AssetConstants.ALL_METALS;
import static com.example.assets.constants.AssetConstants.CRYPTOS;
import static com.example.assets.constants.AssetConstants.CURRENCIES;
import static com.example.assets.constants.AssetConstants.METALS;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AssetServicesTest {

    @Test
    public void should_return_currency_rates() throws Exception {
        shouldReturnRates(CURRENCIES, ALL_CURRENCIES);
    }

    @Test
    public void should_return_crypto_rates() throws Exception {
        shouldReturnRates(CRYPTOS, ALL_CRYPTOS);
    }

    @Test
    public void should_return_metal_rates() throws Exception {
        shouldReturnRates(METALS, ALL_METALS);
    }

    @Test
    public void should_use_retrofit() {

        RateFacade facade = new RateFacade();

        facade.getRates().forEach(x->System.out.println(x.getSymbol() + " " + x.getValue()));
    }

    private void shouldReturnRates(String assetsName, List<String> assetsList) throws Exception {
        // when
        JSONObject rates = AssetServices.getRates(assetsName);

        // then
        assertNotNull(rates);
        Set<String> actualCurrencies = new HashSet<>();
        rates.keys().forEachRemaining(actualCurrencies::add);

        Set actualRates = actualCurrencies
                .stream()
                .map(key -> {
                    try { return rates.get(key); }
                    catch (JSONException e) { e.printStackTrace(); return null; }})
                .collect(Collectors.toSet());

        Set<String> expectedCurrencies = new HashSet<>(assetsList);

        assertSetEquals(actualCurrencies, expectedCurrencies);
        assertAllAssetsHaveSomeValue(actualRates);
    }

    private void assertSetEquals(Set<String> set1, Set<String> set2) {
        if (!(set1.containsAll(set2) && set2.containsAll(set1))) {
            fail("Sets are different: " +
                    "\nset1" + set1.toString() +
                    "\nset2" + set2.toString());
        }
    }

    private void assertAllAssetsHaveSomeValue(Set set) {
        set.forEach(value -> {
            assertNotEquals(value, "");
            assertTrue(Utils.toFloat(value) > 0);
        });
    }
}



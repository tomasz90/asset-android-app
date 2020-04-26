package com.example.assets;

import com.example.assets.util.AssetServices;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    boolean wasCalled = false;

    JSONObject first = new JSONObject()
            .put("currencies",
                    new JSONObject()
                            .put("CHF", "4.10")
                            .put("USD", "4.20"))
            .put("crypto",
                    new JSONObject()
                            .put("BTC", "5500")
                            .put("ETH", "150"));

    JSONObject second = new JSONObject()
            .put("currencies",
                    new JSONObject()
                            .put("CHF", "4.23")
                            .put("USD", "4.67"))
            .put("crypto",
                    new JSONObject()
                            .put("BTC", "6513")
                            .put("ETH", "252"));

    public ExampleUnitTest() throws JSONException {
    }

    @Test
    public void addition_isCorrect() throws Exception {
        JSONObject object = AssetServices.getCryptoRates();
    }

    @Test
    public void whenEntryLiveTimeExpire_thenEviction() throws InterruptedException {


        CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
            @Override
            public JSONObject load(String key) throws JSONException {
                System.out.println("loaded invoked");
                return getData(key);
            }
        };

        LoadingCache<String, JSONObject> cache;
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(loader);


        System.out.println(cache.getUnchecked("crypto"));
        System.out.println(cache.getIfPresent("crypto"));
        System.out.println(cache.getIfPresent("crypto"));
        System.out.println(cache.getIfPresent("crypto"));
        System.out.println(cache.getIfPresent("crypto"));
        Thread.sleep(3000);
        System.out.println(cache.getUnchecked("crypto"));
    }

    JSONObject getData(String type) throws JSONException {
        if (!wasCalled) {
            wasCalled = true;
            System.out.println("get first");
            return first.getJSONObject(type);
        } else {
            System.out.println("get second");
            return second.getJSONObject(type);
        }
    }


}
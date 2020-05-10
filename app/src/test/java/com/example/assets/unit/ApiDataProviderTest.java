package com.example.assets.unit;

import android.app.Application;
import android.os.AsyncTask;

import com.example.assets.util.ApiDataProvider;
import com.google.common.cache.LoadingCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiDataProvider.class, AsyncTask.class})
public class ApiDataProviderTest {

    @Mock
    AsyncTask task;

    @Mock
    Application application;

    @Mock
    LoadingCache cache;

    @Mock
    ApiDataProvider.DataUpdater updater;

    @Test
    public void should_return_fresh_data_when_clean_cache_is_true_and_has_connection() throws Exception {
        // given
        ApiDataProvider apiDataProvider = spy(new ApiDataProvider(application));
        doReturn(true).when(apiDataProvider,"isConnected");
        mockStatic(ApiDataProvider.class);
        doReturn(task).when(ApiDataProvider.class, "getAsync", updater);
        Whitebox.setInternalState(ApiDataProvider.class, "cache", cache);

        // when
        apiDataProvider.getData(true, updater);

        // then
        verify(cache, times(1)).invalidateAll();
        verifyPrivate(ApiDataProvider.class, times(1)).invoke("getAsync", updater);
    }
}

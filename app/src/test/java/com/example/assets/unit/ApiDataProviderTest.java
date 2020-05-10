package com.example.assets.unit;

import android.app.Application;
import android.os.AsyncTask;

import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.Dialog;
import com.google.common.cache.LoadingCache;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static com.example.assets.constants.Constants.MESSAGE_NETWORK_MISSING;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiDataProvider.class, AsyncTask.class, Dialog.class})
public class ApiDataProviderTest {

    private static ApiDataProvider apiDataProvider;

    @Mock
    private static Application application;

    @Mock
    private AsyncTask task;

    @Mock
    private LoadingCache cache;

    @Mock
    private ApiDataProvider.DataUpdater updater;


    @BeforeClass
    public static void setUpStatic() {
        // given
        mockStatic(ApiDataProvider.class);
        mockStatic(Dialog.class);
        apiDataProvider = spy(new ApiDataProvider(application));
    }

    @Before
    public void setUpNonStatic() throws Exception {
        // given
        doReturn(task).when(ApiDataProvider.class, "getAsync", updater);
        Whitebox.setInternalState(ApiDataProvider.class, "cache", cache);
    }

    @Test
    public void should_push_data_when_force_clean_cache_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = true;
        doReturn(connectionAvailable).when(apiDataProvider,"isConnected");

        // when
        apiDataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, only()).invalidateAll();
        verifyPrivate(ApiDataProvider.class, times(1)).invoke("getAsync", updater);
    }

    @Test
    public void should_not_push_data_when_force_clean_cache_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = false;
        doReturn(connectionAvailable).when(apiDataProvider,"isConnected");
        doNothing().when(Dialog.class, "displayToast", application, MESSAGE_NETWORK_MISSING);

        // when
        apiDataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(1)); Dialog.displayToast(application, MESSAGE_NETWORK_MISSING);
        verifyPrivate(ApiDataProvider.class, times(0)).invoke("getAsync", updater);
    }
}

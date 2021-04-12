package com.example.assets.unit;

import android.app.Application;
import android.os.AsyncTask;

import com.example.assets.AppContainer;
import com.example.assets.R;
import com.example.assets.api.DataProvider;
import com.example.assets.util.Dialog;
import com.google.common.cache.LoadingCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
@PrepareForTest({DataProvider.class, AsyncTask.class, Dialog.class})
public class DataProviderTest {

    private static DataProvider dataProvider;

    @Mock
    private static Application application;

    @Mock
    private AsyncTask task;

    @Mock
    private LoadingCache cache;

    @Mock
    private DataProvider.DataUpdater updater;

    @Before
    public void beforeTest() throws Exception {
        // given
        mockStatic(DataProvider.class);
        mockStatic(Dialog.class);
        dataProvider = spy(new DataProvider(application, AppContainer.Companion.getAppContainer().getRateFacade()));
        doReturn(task).when(DataProvider.class, "getAsync", updater);
        doNothing().when(Dialog.class, "displayToast", application, R.string.network_missing);
        Whitebox.setInternalState(DataProvider.class, "cache", cache);
    }

    @Test
    public void should_push_data_when_force_clean_cache_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = true;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, only()).invalidateAll();
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater);
    }

    @Test
    public void should_not_push_data_when_force_clean_cache_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = false;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(1)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(0)).invoke("getAsync", updater);
    }

    @Test
    public void should_not_push_data_when_do_not_clean_cache_no_data_in_cache_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean isCacheDataAvailable = false;
        boolean connectionAvailable = false;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");
        doReturn(isCacheDataAvailable).when(dataProvider, "isCacheDataAvailable");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(1)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(0)).invoke("getAsync", updater);
    }

    @Test
    public void should_push_data_when_do_not_clean_cache_data_in_cache_available_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean isCacheDataAvailable = true;
        boolean connectionAvailable = false;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");
        doReturn(isCacheDataAvailable).when(dataProvider, "isCacheDataAvailable");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(0)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater);
    }

    @Test
    public void should_push_data_when_do_not_clean_cache_data_in_cache_available_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean isCacheDataAvailable = true;
        boolean connectionAvailable = true;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");
        doReturn(isCacheDataAvailable).when(dataProvider, "isCacheDataAvailable");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(0)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater);
    }

    @Test
    public void should_push_data_when_do_not_clean_cache_data_in_cache_not_available_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean isCacheDataAvailable = false;
        boolean connectionAvailable = true;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");
        doReturn(isCacheDataAvailable).when(dataProvider, "isCacheDataAvailable");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(cache, times(0)).invalidateAll();
        verifyStatic(times(0)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater);
    }
}

package com.example.assets.unit;

import android.app.Application;
import android.os.AsyncTask;

import com.example.assets.AppContainer;
import com.example.assets.R;
import com.example.assets.api.DataProvider;
import com.example.assets.api.RateFacade;
import com.example.assets.util.Dialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({DataProvider.class, AsyncTask.class, Dialog.class})
public class DataProviderTest {

    private static DataProvider dataProvider;
    private static RateFacade rateFacade;

    @Mock
    private static Application application;

    @Mock
    private AsyncTask task;

    @Mock
    private Map<String, Float> rates;

    @Mock
    private DataProvider.DataUpdater updater;

    @Before
    public void beforeTest() throws Exception {
        // given
        mockStatic(DataProvider.class);
        mockStatic(Dialog.class);
        rateFacade = AppContainer.Companion.getAppContainer().getRateFacade();
        dataProvider = spy(new DataProvider(application, rateFacade));
        doReturn(task).when(DataProvider.class, "getAsync", updater, rateFacade);
        doNothing().when(Dialog.class, "displayToast", application, R.string.network_missing);
        Whitebox.setInternalState(DataProvider.class, "rates", rates);
    }

    @Test
    public void should_pull_data_when_force_clean_cache_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = true;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(rates, only()).clear();
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater, rateFacade);
    }

    @Test
    public void should_display_error_dialog_when_force_clean_cache_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = true;
        boolean connectionAvailable = false;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(rates, times(0)).clear();
        verifyStatic(times(1)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(0)).invoke("getAsync", updater, rateFacade);
    }

    @Test
    public void should_get_temporary_data_when_do_not_clean_cache_and_connection_not_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean connectionAvailable = false;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(rates, times(0)).clear();
        verifyStatic(times(0)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater, rateFacade);
    }

    @Test
    public void should_get_temporary_data_when_do_not_clean_cache_and_connection_available() throws Exception {
        // given
        boolean forceCleanCache = false;
        boolean connectionAvailable = true;

        doReturn(connectionAvailable).when(dataProvider, "isConnected");

        // when
        dataProvider.getData(forceCleanCache, updater);

        // then
        verify(rates, times(0)).clear();
        verifyStatic(times(0)); Dialog.displayToast(application, R.string.network_missing);
        verifyPrivate(DataProvider.class, times(1)).invoke("getAsync", updater, rateFacade);
    }
}

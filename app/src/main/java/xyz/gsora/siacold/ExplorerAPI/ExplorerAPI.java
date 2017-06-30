package xyz.gsora.siacold.ExplorerAPI;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.gsora.siacold.BuildConfig;

/**
 * Created by gsora on 27/06/17.
 * <p>
 * Sia explorer API connector, singleton.
 */
public class ExplorerAPI {
    private static ExplorerAPI ourInstance = new ExplorerAPI();

    private ExplorerAPI() {
    }

    public static ExplorerAPI getInstance() {
        return ourInstance;
    }

    private OkHttpClient verboseIfDebug() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        return httpClient.build();
    }

    private Retrofit buildRxRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://explore.sia.tech")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(verboseIfDebug())
                .build();
    }

    public Observable<Response<UnlockHash>> getUnlockHashInfo(String unlockHash) {
        return buildRxRetrofit().create(InterfaceAPI.class).getUnlockHashTransactionsInfo(unlockHash);
    }
}

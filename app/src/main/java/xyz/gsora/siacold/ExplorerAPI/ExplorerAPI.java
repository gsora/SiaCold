package xyz.gsora.siacold.ExplorerAPI;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private Retrofit buildRxRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://explore.sia.tech")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Observable<Response<Block>> getUnlockHashInfo(String unlockHash) {
        return buildRxRetrofit().create(InterfaceAPI.class).getUnlockHashInfo(unlockHash);
    }
}

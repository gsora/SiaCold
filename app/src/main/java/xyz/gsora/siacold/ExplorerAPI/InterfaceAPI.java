package xyz.gsora.siacold.ExplorerAPI;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gsora on 27/06/17.
 * <p>
 * Retrofit RxJava interface for Sia explorer API.
 */
public interface InterfaceAPI {
    @GET("explorer/hashes/{unlockHash}")
    Observable<Response<UnlockHash>> getUnlockHashTransactionsInfo(@Path("unlockHash") String unlockHash);
}

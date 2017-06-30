package xyz.gsora.siacold.General;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xyz.gsora.siacold.ExplorerAPI.ExplorerAPI;

import java.util.List;

/**
 * Created by gsora on 6/30/17.
 * <p>
 * Fetches balance for all the addresses of a given seed
 */
public class AddressesBalanceFetcher {

    private Double finalResult = null;

    public Observable<Double> fetch(List<Address> addresses) {
        Observable<Double> ret = Observable.create(e -> {
            ExplorerAPI ex = ExplorerAPI.getInstance();
            for (Address a : addresses) {
                ex.getUnlockHashInfo(a.getAddress())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .blockingSubscribe(data -> {
                            if (data.body() != null) {
                                if (finalResult == null) {
                                    finalResult = SiaValue.toSia(a.getAddress(), data.body().getTransactions());
                                } else {
                                    finalResult += SiaValue.toSia(a.getAddress(), data.body().getTransactions());
                                }
                            }
                        });
            }

            if (finalResult == null) {
                finalResult = 0.0;
            }
            e.onNext(finalResult);
            e.onComplete();
        });
        return ret;
    }
}



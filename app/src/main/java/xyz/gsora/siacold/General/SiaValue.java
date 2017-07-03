package xyz.gsora.siacold.General;

import xyz.gsora.siacold.ExplorerAPI.Rawtransaction;
import xyz.gsora.siacold.ExplorerAPI.Siacoinoutput;
import xyz.gsora.siacold.ExplorerAPI.Transaction;

import java.util.List;

/**
 * Created by gsora on 6/30/17.
 * <p>
 * SiaValue takes an array of {@link xyz.gsora.siacold.ExplorerAPI.Transaction} and calculates the Siacoin value.
 */
public class SiaValue {

    private static String TAG = SiaValue.class.getSimpleName();

    public static String toSiaString(String hash, List<Transaction> transactions) {
        return readableCoins(toSia(hash, transactions));
    }

    public static Double toSia(String hash, List<Transaction> transactions) {
        Double result = new Double(0.0f);
        for (Transaction t : transactions) {
            Rawtransaction rt = t.getRawtransaction();
            Siacoinoutput[] siacoinoutputs = rt.getSiacoinoutputs();
            for (int i1 = 0, siacoinoutputsLength = siacoinoutputs.length; i1 < siacoinoutputsLength; i1++) {
                Siacoinoutput so = siacoinoutputs[i1];
                if (so.getUnlockhash().equals(hash)) {
                    result += Double.valueOf(so.getValue().trim());
                }
            }
        }
        return result;
    }

    public static String readableCoins(Double hastings) {
        if (hastings < 1000000000000000000000000.0f) {
            return "0 SC";
        } else if (hastings >= 1000000000000000000000000.0f && hastings <= 2000000000000000000000000.0f) {
            return "1 SC";
        } else if (hastings < 1000000000000000000000000000000000.0f) {
            return String.format("%.2f SC", (hastings / 1000000000000000000000000.0f));
        } else {
            return String.format("%.2f billions SC", (hastings / 1000000000000000000000000000000000.0f));
        }
    }
}

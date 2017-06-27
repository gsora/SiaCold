
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Publickey {

    @SerializedName("algorithm")
    private String mAlgorithm;
    @SerializedName("key")
    private String mKey;

    public String getAlgorithm() {
        return mAlgorithm;
    }

    public void setAlgorithm(String algorithm) {
        mAlgorithm = algorithm;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

}

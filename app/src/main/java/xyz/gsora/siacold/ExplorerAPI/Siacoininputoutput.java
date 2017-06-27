
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Siacoininputoutput {

    @SerializedName("unlockhash")
    private String mUnlockhash;
    @SerializedName("value")
    private String mValue;

    public String getUnlockhash() {
        return mUnlockhash;
    }

    public void setUnlockhash(String unlockhash) {
        mUnlockhash = unlockhash;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}

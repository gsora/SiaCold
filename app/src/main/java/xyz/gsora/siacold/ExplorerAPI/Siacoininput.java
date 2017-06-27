
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Siacoininput {

    @SerializedName("parentid")
    private String mParentid;
    @SerializedName("unlockconditions")
    private Unlockconditions mUnlockconditions;

    public String getParentid() {
        return mParentid;
    }

    public void setParentid(String parentid) {
        mParentid = parentid;
    }

    public Unlockconditions getUnlockconditions() {
        return mUnlockconditions;
    }

    public void setUnlockconditions(Unlockconditions unlockconditions) {
        mUnlockconditions = unlockconditions;
    }

}

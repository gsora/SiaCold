
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Unlockconditions {

    @SerializedName("publickeys")
    private List<Publickey> mPublickeys;
    @SerializedName("signaturesrequired")
    private Long mSignaturesrequired;
    @SerializedName("timelock")
    private Long mTimelock;

    public List<Publickey> getPublickeys() {
        return mPublickeys;
    }

    public void setPublickeys(List<Publickey> publickeys) {
        mPublickeys = publickeys;
    }

    public Long getSignaturesrequired() {
        return mSignaturesrequired;
    }

    public void setSignaturesrequired(Long signaturesrequired) {
        mSignaturesrequired = signaturesrequired;
    }

    public Long getTimelock() {
        return mTimelock;
    }

    public void setTimelock(Long timelock) {
        mTimelock = timelock;
    }

}

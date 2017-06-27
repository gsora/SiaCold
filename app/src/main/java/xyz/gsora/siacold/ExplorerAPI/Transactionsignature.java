
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Transactionsignature {

    @SerializedName("coveredfields")
    private Coveredfields mCoveredfields;
    @SerializedName("parentid")
    private String mParentid;
    @SerializedName("publickeyindex")
    private Long mPublickeyindex;
    @SerializedName("signature")
    private String mSignature;
    @SerializedName("timelock")
    private Long mTimelock;

    public Coveredfields getCoveredfields() {
        return mCoveredfields;
    }

    public void setCoveredfields(Coveredfields coveredfields) {
        mCoveredfields = coveredfields;
    }

    public String getParentid() {
        return mParentid;
    }

    public void setParentid(String parentid) {
        mParentid = parentid;
    }

    public Long getPublickeyindex() {
        return mPublickeyindex;
    }

    public void setPublickeyindex(Long publickeyindex) {
        mPublickeyindex = publickeyindex;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public Long getTimelock() {
        return mTimelock;
    }

    public void setTimelock(Long timelock) {
        mTimelock = timelock;
    }

}

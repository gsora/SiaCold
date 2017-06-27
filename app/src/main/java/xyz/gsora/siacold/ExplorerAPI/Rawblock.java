
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Rawblock {

    @SerializedName("minerpayouts")
    private Object mMinerpayouts;
    @SerializedName("nonce")
    private List<Long> mNonce;
    @SerializedName("parentid")
    private String mParentid;
    @SerializedName("timestamp")
    private Long mTimestamp;
    @SerializedName("transactions")
    private Object mTransactions;

    public Object getMinerpayouts() {
        return mMinerpayouts;
    }

    public void setMinerpayouts(Object minerpayouts) {
        mMinerpayouts = minerpayouts;
    }

    public List<Long> getNonce() {
        return mNonce;
    }

    public void setNonce(List<Long> nonce) {
        mNonce = nonce;
    }

    public String getParentid() {
        return mParentid;
    }

    public void setParentid(String parentid) {
        mParentid = parentid;
    }

    public Long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Long timestamp) {
        mTimestamp = timestamp;
    }

    public Object getTransactions() {
        return mTransactions;
    }

    public void setTransactions(Object transactions) {
        mTransactions = transactions;
    }

}

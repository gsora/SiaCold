
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Rawtransaction {

    @SerializedName("arbitrarydata")
    private Object mArbitrarydata;
    @SerializedName("filecontractrevisions")
    private Object mFilecontractrevisions;
    @SerializedName("filecontracts")
    private Object mFilecontracts;
    @SerializedName("minerfees")
    private Object mMinerfees;
    @SerializedName("siacoininputs")
    private Object mSiacoininputs;
    @SerializedName("siacoinoutputs")
    private Siacoinoutput[] mSiacoinoutputs;
    @SerializedName("siafundinputs")
    private Object mSiafundinputs;
    @SerializedName("siafundoutputs")
    private Object mSiafundoutputs;
    @SerializedName("storageproofs")
    private Object mStorageproofs;
    @SerializedName("transactionsignatures")
    private Object mTransactionsignatures;

    public Object getArbitrarydata() {
        return mArbitrarydata;
    }

    public void setArbitrarydata(Object arbitrarydata) {
        mArbitrarydata = arbitrarydata;
    }

    public Object getFilecontractrevisions() {
        return mFilecontractrevisions;
    }

    public void setFilecontractrevisions(Object filecontractrevisions) {
        mFilecontractrevisions = filecontractrevisions;
    }

    public Object getFilecontracts() {
        return mFilecontracts;
    }

    public void setFilecontracts(Object filecontracts) {
        mFilecontracts = filecontracts;
    }

    public Object getMinerfees() {
        return mMinerfees;
    }

    public void setMinerfees(Object minerfees) {
        mMinerfees = minerfees;
    }

    public Object getSiacoininputs() {
        return mSiacoininputs;
    }

    public void setSiacoininputs(Object siacoininputs) {
        mSiacoininputs = siacoininputs;
    }

    public Siacoinoutput[] getSiacoinoutputs() {
        return mSiacoinoutputs;
    }

    public void setSiacoinoutputs(Siacoinoutput[] siacoinoutputs) {
        mSiacoinoutputs = siacoinoutputs;
    }

    public Object getSiafundinputs() {
        return mSiafundinputs;
    }

    public void setSiafundinputs(Object siafundinputs) {
        mSiafundinputs = siafundinputs;
    }

    public Object getSiafundoutputs() {
        return mSiafundoutputs;
    }

    public void setSiafundoutputs(Object siafundoutputs) {
        mSiafundoutputs = siafundoutputs;
    }

    public Object getStorageproofs() {
        return mStorageproofs;
    }

    public void setStorageproofs(Object storageproofs) {
        mStorageproofs = storageproofs;
    }

    public Object getTransactionsignatures() {
        return mTransactionsignatures;
    }

    public void setTransactionsignatures(Object transactionsignatures) {
        mTransactionsignatures = transactionsignatures;
    }

}

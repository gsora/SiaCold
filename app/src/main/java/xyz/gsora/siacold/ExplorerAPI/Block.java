
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Block {

    @SerializedName("activecontractcost")
    private String mActivecontractcost;
    @SerializedName("activecontractcount")
    private Long mActivecontractcount;
    @SerializedName("activecontractsize")
    private String mActivecontractsize;
    @SerializedName("arbitrarydatacount")
    private Long mArbitrarydatacount;
    @SerializedName("blockid")
    private String mBlockid;
    @SerializedName("difficulty")
    private String mDifficulty;
    @SerializedName("estimatedhashrate")
    private String mEstimatedhashrate;
    @SerializedName("filecontractcount")
    private Long mFilecontractcount;
    @SerializedName("filecontractrevisioncount")
    private Long mFilecontractrevisioncount;
    @SerializedName("height")
    private Long mHeight;
    @SerializedName("maturitytimestamp")
    private Long mMaturitytimestamp;
    @SerializedName("minerfeecount")
    private Long mMinerfeecount;
    @SerializedName("minerpayoutcount")
    private Long mMinerpayoutcount;
    @SerializedName("minerpayoutids")
    private Object mMinerpayoutids;
    @SerializedName("rawblock")
    private Rawblock mRawblock;
    @SerializedName("siacoininputcount")
    private Long mSiacoininputcount;
    @SerializedName("siacoinoutputcount")
    private Long mSiacoinoutputcount;
    @SerializedName("siafundinputcount")
    private Long mSiafundinputcount;
    @SerializedName("siafundoutputcount")
    private Long mSiafundoutputcount;
    @SerializedName("storageproofcount")
    private Long mStorageproofcount;
    @SerializedName("target")
    private List<Long> mTarget;
    @SerializedName("totalcoins")
    private String mTotalcoins;
    @SerializedName("totalcontractcost")
    private String mTotalcontractcost;
    @SerializedName("totalcontractsize")
    private String mTotalcontractsize;
    @SerializedName("totalrevisionvolume")
    private String mTotalrevisionvolume;
    @SerializedName("transactioncount")
    private Long mTransactioncount;
    @SerializedName("transactions")
    private Object mTransactions;
    @SerializedName("transactionsignaturecount")
    private Long mTransactionsignaturecount;

    public String getActivecontractcost() {
        return mActivecontractcost;
    }

    public void setActivecontractcost(String activecontractcost) {
        mActivecontractcost = activecontractcost;
    }

    public Long getActivecontractcount() {
        return mActivecontractcount;
    }

    public void setActivecontractcount(Long activecontractcount) {
        mActivecontractcount = activecontractcount;
    }

    public String getActivecontractsize() {
        return mActivecontractsize;
    }

    public void setActivecontractsize(String activecontractsize) {
        mActivecontractsize = activecontractsize;
    }

    public Long getArbitrarydatacount() {
        return mArbitrarydatacount;
    }

    public void setArbitrarydatacount(Long arbitrarydatacount) {
        mArbitrarydatacount = arbitrarydatacount;
    }

    public String getBlockid() {
        return mBlockid;
    }

    public void setBlockid(String blockid) {
        mBlockid = blockid;
    }

    public String getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(String difficulty) {
        mDifficulty = difficulty;
    }

    public String getEstimatedhashrate() {
        return mEstimatedhashrate;
    }

    public void setEstimatedhashrate(String estimatedhashrate) {
        mEstimatedhashrate = estimatedhashrate;
    }

    public Long getFilecontractcount() {
        return mFilecontractcount;
    }

    public void setFilecontractcount(Long filecontractcount) {
        mFilecontractcount = filecontractcount;
    }

    public Long getFilecontractrevisioncount() {
        return mFilecontractrevisioncount;
    }

    public void setFilecontractrevisioncount(Long filecontractrevisioncount) {
        mFilecontractrevisioncount = filecontractrevisioncount;
    }

    public Long getHeight() {
        return mHeight;
    }

    public void setHeight(Long height) {
        mHeight = height;
    }

    public Long getMaturitytimestamp() {
        return mMaturitytimestamp;
    }

    public void setMaturitytimestamp(Long maturitytimestamp) {
        mMaturitytimestamp = maturitytimestamp;
    }

    public Long getMinerfeecount() {
        return mMinerfeecount;
    }

    public void setMinerfeecount(Long minerfeecount) {
        mMinerfeecount = minerfeecount;
    }

    public Long getMinerpayoutcount() {
        return mMinerpayoutcount;
    }

    public void setMinerpayoutcount(Long minerpayoutcount) {
        mMinerpayoutcount = minerpayoutcount;
    }

    public Object getMinerpayoutids() {
        return mMinerpayoutids;
    }

    public void setMinerpayoutids(Object minerpayoutids) {
        mMinerpayoutids = minerpayoutids;
    }

    public Rawblock getRawblock() {
        return mRawblock;
    }

    public void setRawblock(Rawblock rawblock) {
        mRawblock = rawblock;
    }

    public Long getSiacoininputcount() {
        return mSiacoininputcount;
    }

    public void setSiacoininputcount(Long siacoininputcount) {
        mSiacoininputcount = siacoininputcount;
    }

    public Long getSiacoinoutputcount() {
        return mSiacoinoutputcount;
    }

    public void setSiacoinoutputcount(Long siacoinoutputcount) {
        mSiacoinoutputcount = siacoinoutputcount;
    }

    public Long getSiafundinputcount() {
        return mSiafundinputcount;
    }

    public void setSiafundinputcount(Long siafundinputcount) {
        mSiafundinputcount = siafundinputcount;
    }

    public Long getSiafundoutputcount() {
        return mSiafundoutputcount;
    }

    public void setSiafundoutputcount(Long siafundoutputcount) {
        mSiafundoutputcount = siafundoutputcount;
    }

    public Long getStorageproofcount() {
        return mStorageproofcount;
    }

    public void setStorageproofcount(Long storageproofcount) {
        mStorageproofcount = storageproofcount;
    }

    public List<Long> getTarget() {
        return mTarget;
    }

    public void setTarget(List<Long> target) {
        mTarget = target;
    }

    public String getTotalcoins() {
        return mTotalcoins;
    }

    public void setTotalcoins(String totalcoins) {
        mTotalcoins = totalcoins;
    }

    public String getTotalcontractcost() {
        return mTotalcontractcost;
    }

    public void setTotalcontractcost(String totalcontractcost) {
        mTotalcontractcost = totalcontractcost;
    }

    public String getTotalcontractsize() {
        return mTotalcontractsize;
    }

    public void setTotalcontractsize(String totalcontractsize) {
        mTotalcontractsize = totalcontractsize;
    }

    public String getTotalrevisionvolume() {
        return mTotalrevisionvolume;
    }

    public void setTotalrevisionvolume(String totalrevisionvolume) {
        mTotalrevisionvolume = totalrevisionvolume;
    }

    public Long getTransactioncount() {
        return mTransactioncount;
    }

    public void setTransactioncount(Long transactioncount) {
        mTransactioncount = transactioncount;
    }

    public Object getTransactions() {
        return mTransactions;
    }

    public void setTransactions(Object transactions) {
        mTransactions = transactions;
    }

    public Long getTransactionsignaturecount() {
        return mTransactionsignaturecount;
    }

    public void setTransactionsignaturecount(Long transactionsignaturecount) {
        mTransactionsignaturecount = transactionsignaturecount;
    }

}

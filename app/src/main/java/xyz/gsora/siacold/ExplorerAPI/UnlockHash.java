
package xyz.gsora.siacold.ExplorerAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UnlockHash {

    @SerializedName("block")
    private Block mBlock;
    @SerializedName("blocks")
    private Object mBlocks;
    @SerializedName("hashtype")
    private String mHashtype;
    @SerializedName("transaction")
    private Transaction mTransaction;
    @SerializedName("transactions")
    private List<Transaction> mTransactions;

    public Block getBlock() {
        return mBlock;
    }

    public void setBlock(Block block) {
        mBlock = block;
    }

    public Object getBlocks() {
        return mBlocks;
    }

    public void setBlocks(Object blocks) {
        mBlocks = blocks;
    }

    public String getHashtype() {
        return mHashtype;
    }

    public void setHashtype(String hashtype) {
        mHashtype = hashtype;
    }

    public Transaction getTransaction() {
        return mTransaction;
    }

    public void setTransaction(Transaction transaction) {
        mTransaction = transaction;
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
    }

}

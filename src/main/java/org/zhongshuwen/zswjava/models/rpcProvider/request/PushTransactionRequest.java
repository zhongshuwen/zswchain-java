package org.zhongshuwen.zswjava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The request of PushTransactionRequest RPC call.
 */
public class PushTransactionRequest extends SendTransactionRequest {

    /**
     * Instantiates a new PushTransactionRequest.
     *
     * @param signatures the list of signatures required to authorize transaction
     * @param compression the compression used, usually 0.
     * @param packagedContextFreeData the context free data in hex
     * @param packTrx the packed Transaction (serialized transaction). It is serialized version
     * of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     */
    public PushTransactionRequest(@NotNull List<String> signatures, int compression,
            String packagedContextFreeData, @NotNull String packTrx) {
        super(signatures, compression, packagedContextFreeData, packTrx);
    }

    /**
     * Gets list of signatures required to authorize transaction.
     *
     * @return the list of signatures
     */
    @NotNull
    public List<String> getSignatures() {
        return super.getSignatures();
    }

    /**
     * Sets list of signatures required to authorize transaction.
     *
     * @param signatures the list of signatures.
     */
    public void setSignatures(@NotNull List<String> signatures) {
        super.setSignatures(signatures);
    }

    /**
     * Gets the compression used, usually 0.
     *
     * @return the compression.
     */
    public int getCompression() {
        return super.getCompression();
    }

    /**
     * Sets the compression used, usually 0.
     *
     * @param compression the compression.
     */
    public void setCompression(int compression) {
        super.setCompression(compression);
    }

    /**
     * Gets packaged context free data in hex.
     *
     * @return the packaged context free data in hex.
     */
    public String getPackagedContextFreeData() {
        return super.getPackagedContextFreeData();
    }

    /**
     * Sets packaged context free data in hex
     *
     * @param packagedContextFreeData the packaged context free data in hex.
     */
    public void setPackagedContextFreeData(String packagedContextFreeData) {
        super.setPackagedContextFreeData(packagedContextFreeData);
    }

    /**
     * Gets the packed transaction (serialized transaction).
     * <br> It is serialized version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     *
     * @return the Pack Transaction (Serialized Transaction).
     */
    @NotNull
    public String getPackTrx() {
        return super.getPackTrx();
    }

    /**
     * Sets the packed transaction (serialized transaction).
     * <br> It is the serialized version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     *
     * @param packTrx the packed transaction (serialized transaction).
     */
    public void setPackTrx(@NotNull String packTrx) {
        super.setPackTrx(packTrx);
    }
}

package org.zhongshuwen.zswjava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * The request class of getRawAbi() RPC call {@link org.zhongshuwen.zswjava.interfaces.IRPCProvider#getRawAbi(GetRawAbiRequest)}
 */
public class GetRawAbiRequest {

    /**
     * Instantiates a new GetRawAbiRequest.
     *
     * @param accountName the String representation of ZSWCHAIN name type
     */
    public GetRawAbiRequest(@NotNull String accountName) {
        this.accountName = accountName;
    }

    /**
     * The string representation of ZSWCHAIN name type
     */
    @SerializedName("account_name")
    @NotNull
    private String accountName;

    /**
     * Gets the string representation of ZSWCHAIN name type
     *
     * @return the string representation of ZSWCHAIN name type
     */
    @NotNull
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the string representation of ZSWCHAIN name type
     *
     * @param accountName the string representation of ZSWCHAIN name type
     */
    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }
}

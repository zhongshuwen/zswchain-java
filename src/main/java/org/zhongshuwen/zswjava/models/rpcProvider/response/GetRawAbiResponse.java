package org.zhongshuwen.zswjava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetRawAbiRequest;

/**
 * The response of getRawAbi() RPC call {@link org.zhongshuwen.zswjava.interfaces.IRPCProvider#getRawAbi(GetRawAbiRequest)}
 */
public class GetRawAbiResponse {

    /**
     * The account name (contract name) found in {@link org.zhongshuwen.zswjava.models.ZSWName}
     */
    @SerializedName("account_name")
    private String accountName;

    @SerializedName("code_hash")
    private String codeHash;

    @SerializedName("abi_hash")
    private String abiHash;

    /**
     * The ABI (raw ABI) of the account name (contract name)
     * <br>
     * This ABI is used to serialize a contract's action data.
     */
    @SerializedName("abi")
    private String abi;

    /**
     * Gets the account name (contract name) found in {@link org.zhongshuwen.zswjava.models.ZSWName}
     *
     * @return the account name.
     */
    public String getAccountName() {
        return accountName;
    }

    public String getCodeHash() {
        return codeHash;
    }

    public String getAbiHash() {
        return abiHash;
    }

    /**
     * Gets The ABI (raw ABI) of the account name (contract name).
     * <br>
     *      This ABI is used to serialize a contract's action data.
     *
     * @return the raw ABI
     */
    public String getAbi() {
        return abi;
    }
}

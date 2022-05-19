package org.zhongshuwen.zswjava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetRequiredKeysRequest;

/**
 * The response of getRequiredKeys() RPC call {@link org.zhongshuwen.zswjava.interfaces.IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)}
 */
public class GetRequiredKeysResponse {

    /**
     * The required public ZSWCHAIN keys to sign the transaction. It gets assigned to {@link
     * org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureRequest#setSigningPublicKeys(List)},
     * which is passed to a Signature Provider to sign a transaction.
     */
    @SerializedName("required_keys")
    private List<String> requiredKeys;

    /**
     * Gets the required public ZSWCHAIN keys to sign the transaction. It gets assigned to {@link
     * org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureRequest#setSigningPublicKeys(List)},
     * which is passed to a Signature Provider to sign a transaction.
     * @return The required public ZSWCHAIN keys.
     */
    public List<String> getRequiredKeys() {
        return requiredKeys;
    }
}

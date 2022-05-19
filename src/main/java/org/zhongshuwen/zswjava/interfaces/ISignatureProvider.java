package org.zhongshuwen.zswjava.interfaces;

import java.util.List;
import org.zhongshuwen.zswjava.error.signatureProvider.GetAvailableKeysError;
import org.zhongshuwen.zswjava.error.signatureProvider.SignTransactionError;
import org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureRequest;
import org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of Signature provider.
 */
public interface ISignatureProvider {

    /**
     * Sign a transaction in Signature Provider <br> Check signTransaction flow() in "complete
     * workflow" for more detail
     *
     * @param zswhqTransactionSignatureRequest the request
     * @return the response
     * @throws SignTransactionError thrown if there are any exceptions during the signing process.
     */
    @NotNull
    ZswChainTransactionSignatureResponse signTransaction(
            @NotNull ZswChainTransactionSignatureRequest zswhqTransactionSignatureRequest)
            throws SignTransactionError;

    /**
     * Gets available keys from signature provider <br> Check createSignatureRequest() flow in
     * "complete workflow" for more detail of how the method is used
     *
     * @return the available keys of signature provider in ZSW format
     * @throws GetAvailableKeysError thrown if there are any exceptions during the get available keys process.
     */
    @NotNull
    List<String> getAvailableKeys() throws GetAvailableKeysError;
}

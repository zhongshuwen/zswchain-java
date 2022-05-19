package org.zhongshuwen.zswjava.models.signatureProvider;

import java.util.List;
import org.zhongshuwen.zswjava.error.signatureProvider.SignatureProviderError;
import org.zhongshuwen.zswjava.models.rpcProvider.ContextFreeData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The response object returned from the SignatureProvider after the transaction has been signed.
 */
public class ZswChainTransactionSignatureResponse {

    /**
     * The serialized (Hex) version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link org.zhongshuwen.zswjava.interfaces.ISerializationProvider#serializeTransaction(String)}
     * <br>
     * The transaction could have been modified by the signature provider.
     * <br>
     * If signature provider modifies the serialized transaction returned in the response {@link
     * ZswChainTransactionSignatureResponse#getSerializedTransaction()} but {@link
     * ZswChainTransactionSignatureRequest#isModifiable()} is false then {@link
     * org.zhongshuwen.zswjava.error.session.TransactionGetSignatureNotAllowModifyTransactionError} will
     * be thrown
     */
    @NotNull
    private String serializedTransaction;

    /**
     * The serialized (Hex) version of {@link org.zhongshuwen.zswjava.models.rpcProvider.ContextFreeData}.
     * <br>
     * It is the result of {@link ContextFreeData#getSerialized()}
     */
    @NotNull
    private String serializedContextFreeData;

    /**
     * The signatures that are signed by private keys of {@link ZswChainTransactionSignatureRequest#getSigningPublicKeys()}
     */
    @NotNull
    private List<String> signatures;

    /**
     * The error that occurred during signing.
     */
    @Nullable
    private SignatureProviderError error;

    public ZswChainTransactionSignatureResponse(@NotNull String serializedTransaction, @NotNull String serializedContextFreeData,
            @NotNull List<String> signatures, @Nullable SignatureProviderError error) {
        this.serializedTransaction = serializedTransaction;
        this.serializedContextFreeData = serializedContextFreeData;
        this.signatures = signatures;
        this.error = error;
    }

    public ZswChainTransactionSignatureResponse(@NotNull String serializedTransaction,
            @NotNull List<String> signatures, @Nullable SignatureProviderError error) {
        this(serializedTransaction, "", signatures, error);
    }

    /**
     * Gets the serialized transaction.
     *
     * @return the serialize transaction
     */
    @NotNull
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    /**
     * Gets signatures.
     *
     * @return the signatures
     */
    @NotNull
    public List<String> getSignatures() {
        return signatures;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    @Nullable
    public SignatureProviderError getError() {
        return error;
    }

    /**
     * Gets serialized context free data.
     *
     * @return the serialized context free data
     */
    @NotNull
    public String getSerializedContextFreeData() { return serializedContextFreeData; }
}

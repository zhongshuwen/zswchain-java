package org.zhongshuwen.zswjava.models.signatureProvider;

import java.util.List;
import org.zhongshuwen.zswjava.models.rpcProvider.ContextFreeData;
import org.zhongshuwen.zswjava.models.rpcProvider.response.GetInfoResponse;
import org.bouncycastle.util.encoders.Hex;

/**
 * The request object that will be sent to SignatureProvider.  It contains the transaction that will
 * need to be signed.
 */
public class ZswChainTransactionSignatureRequest {

    /**
     * The serialized (Hex) version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link org.zhongshuwen.zswjava.interfaces.ISerializationProvider#serializeTransaction(String)}
     */
    private String serializedTransaction;

    /**
     * The serialized (Hex) version of all concatenated context free data
     */
    private String serializedContextFreeData;

    /**
     * The ZSWCHAIN public keys which will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     */
    private List<String> signingPublicKeys;

    /**
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     */
    private String chainId;

    /**
     * The list of ABIs passed to the signature provider.
     */
    private List<BinaryAbi> abis;

    /**
     * Whether the serialized transaction is modifiable.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * ZswChainTransactionSignatureResponse#getSerializedTransaction()} and this field is false then
     * {@link org.zhongshuwen.zswjava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     */
    private boolean isModifiable;

    /**
     * Instantiates a new ZswChain transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKeys the signing public keys
     * @param chainId the chain id
     * @param abis the ABIs
     * @param isModifiable boolean to indicate whether the signature provider is able to modify the
     * transaction
     * @param serializedContextFreeData the serialized contextFreeData
     */
    public ZswChainTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKeys, String chainId, List<BinaryAbi> abis,
            boolean isModifiable, String serializedContextFreeData) {
        this.serializedTransaction = serializedTransaction;
        this.signingPublicKeys = signingPublicKeys;
        this.chainId = chainId;
        this.abis = abis;
        this.isModifiable = isModifiable;
        this.setSerializedContextFreeData(serializedContextFreeData);
    }

    /**
     * Instantiates a new ZswChain transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKeys the signing public keys
     * @param chainId the chain id
     * @param abis the ABIs
     * @param isModifiable boolean to indicate whether the signature provider is able to modify the
     * transaction
     */
    public ZswChainTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKeys, String chainId, List<BinaryAbi> abis,
            boolean isModifiable) {
        this(serializedTransaction, signingPublicKeys, chainId, abis, isModifiable, "");
    }

    /**
     * Gets the serialized transaction.
     * <br>
     * The serialized (Hex) version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link org.zhongshuwen.zswjava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @return the serialized transaction
     */
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    /**
     * Sets the serialized transaction.
     * <br>
     * The serialized (Hex) version of {@link org.zhongshuwen.zswjava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link org.zhongshuwen.zswjava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @param serializedTransaction the serialized transaction
     */
    public void setSerializedTransaction(String serializedTransaction) {
        this.serializedTransaction = serializedTransaction;
    }

    /**
     * Gets the serialized contextFreeData.
     * It is the result of {@link ContextFreeData#getSerialized()}
     *
     * @return the serialized contextFreeData
     */
    public String getSerializedContextFreeData() {
        return serializedContextFreeData;
    }

    /**
     * Sets the serialized contextFreeData.
     * It is the result of {@link ContextFreeData#getSerialized()}
     *
     * @param serializedContextFreeData the serialized contextFreeData
     */
    public void setSerializedContextFreeData(String serializedContextFreeData) {
        this.serializedContextFreeData = serializedContextFreeData.isEmpty() ? Hex.toHexString(new byte[32]) : serializedContextFreeData;
    }

    /**
     * Gets the signing public key.
     * <br>
     * The ZSWCHAIN public key that will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     *
     * @return the signing public key
     */
    public List<String> getSigningPublicKeys() {
        return signingPublicKeys;
    }

    /**
     * Sets the signing public key.
     * <br>
     * The ZSWCHAIN public key that will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     *
     * @param signingPublicKeys the signing public key
     */
    public void setSigningPublicKeys(List<String> signingPublicKeys) {
        this.signingPublicKeys = signingPublicKeys;
    }

    /**
     * Gets the chain id.
     * <br>
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @return the chain id
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Sets the chain id.
     * <br>
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @param chainId the chain id
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    /**
     * Gets ABIs.
     * <br>
     * The list of ABIs that is passed to the signature provider.
     *
     * @return the abis
     */
    public List<BinaryAbi> getAbis() {
        return abis;
    }

    /**
     * Sets ABIs.
     * <br>
     * The list of ABIs that are passed to the signature provider.
     *
     * @param abis the abis
     */
    public void setAbis(List<BinaryAbi> abis) {
        this.abis = abis;
    }

    /**
     * isModifiable boolean.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * ZswChainTransactionSignatureResponse#getSerializedTransaction()} but this field is false then
     * {@link org.zhongshuwen.zswjava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     *
     * @return the boolean
     */
    public boolean isModifiable() {
        return isModifiable;
    }

    /**
     * Sets isModifiable.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * ZswChainTransactionSignatureResponse#getSerializedTransaction()} but this field is false then
     * {@link org.zhongshuwen.zswjava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     *
     * @param modifiable the modifiable
     */
    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }
}

package org.zhongshuwen.zswjava;


import org.zhongshuwen.zswjava.enums.AlgorithmEmployed;
import org.zhongshuwen.zswjava.error.ZswChainError;
import org.zhongshuwen.zswjava.error.ErrorConstants;
import org.zhongshuwen.zswjava.error.utilities.Base58ManipulationError;
import org.zhongshuwen.zswjava.error.signatureProvider.GetAvailableKeysError;
import org.zhongshuwen.zswjava.error.signatureProvider.SignTransactionError;
import org.zhongshuwen.zswjava.error.utilities.ZSWFormatterError;
import org.zhongshuwen.zswjava.error.utilities.ZSWFormatterSignatureIsNotCanonicalError;
import org.zhongshuwen.zswjava.error.utilities.PEMProcessorError;
import org.zhongshuwen.zswjava.interfaces.ISignatureProvider;
import org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureRequest;
import org.zhongshuwen.zswjava.models.signatureProvider.ZswChainTransactionSignatureResponse;
import org.zhongshuwen.zswjava.utilities.ZSWFormatter;
import org.zhongshuwen.zswjava.utilities.PEMProcessor;
import org.zhongshuwen.zswjava.error.ImportKeyError;
import org.zhongshuwen.zswjava.error.SoftKeySignatureErrorConstants;
import org.zhongshuwen.zswjava.utilities.SM2Formatter;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.zz.gmhelper.BCECUtil;
import org.zz.gmhelper.SM2Util;
import org.zz.gmhelper.cert.SM2PrivateKey;

import java.math.BigInteger;
import java.rmi.server.ExportException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * Example signature provider implementation for ZSWCHAIN-java SDK that signs transactions using
 * an in-memory private key generated with the secp256r1, prime256v1, or secp256k1 algorithms.  This
 * implementation is NOT secure and should only be used for educational purposes.  It is NOT
 * advisable to store private keys outside of secure devices like TEE's and SE's.
 */
public class SoftSM2KeySignatureProviderImpl implements ISignatureProvider {

    /**
     * Keep a Set (Unique) of private keys in PEM format
     */
    private Map<String, byte[]> keyMap = new HashMap<String, byte[]>();


    /**
     * Maximum number of times the signature provider should try to create a canonical signature
     * for a secp256k1 generated key when the attempt fails.
     */
    private static final int MAX_NOT_CANONICAL_RE_SIGN = 100;

    /**
     * Index of R value in the signature result of softkey signing
     */
    private static final int R_INDEX = 0;

    /**
     * Index of S value in the signature result of softkey signing
     */
    private static final int S_INDEX = 1;

    /**
     * Signum to convert a negative value to a positive Big Integer
     */
    private static final int BIG_INTEGER_POSITIVE = 1;

    /**
     * Import private key into softkey signature provider.  Private key is stored in memory.
     * NOT RECOMMENDED for production use!!!!
     *
     * @param privateKey - Eos format private key
     * @throws ImportKeyError Exception that occurs while trying to import a key
     */
    public void importKey(@NotNull String privateKey) throws ImportKeyError {
        if (privateKey.isEmpty()) {
            throw new ImportKeyError(SoftKeySignatureErrorConstants.IMPORT_KEY_INPUT_EMPTY_ERROR);
        }
        if(!privateKey.substring(0,7).equals("PVT_GM_")){
            throw new ImportKeyError(ErrorConstants.UNSUPPORTED_ALGORITHM);
        }
        try {
            byte[] rawD = ZSWFormatter.decodePrivateKey(privateKey.substring(7), AlgorithmEmployed.SM2P256V1);
            //BCECPrivateKey pKey = SM2Util.getBCECPrivateKeyFromRawD(rawD);
            String zswPublicKey = SM2Util.convertPrivateKeyRawDToZSWPublicKey(rawD);
            this.keyMap.put(zswPublicKey, rawD);


        }catch (org.zhongshuwen.zswjava.error.utilities.Base58ManipulationError e){
            throw new ImportKeyError(e);

        }
    }

    @Override
    public @NotNull ZswChainTransactionSignatureResponse signTransaction(@NotNull ZswChainTransactionSignatureRequest zswhqTransactionSignatureRequest) throws SignTransactionError {

        if (zswhqTransactionSignatureRequest.getSigningPublicKeys().isEmpty()) {
            throw new SignTransactionError(SoftKeySignatureErrorConstants.SIGN_TRANS_EMPTY_KEY_LIST);

        }

        if (zswhqTransactionSignatureRequest.getChainId().isEmpty()) {
            throw new SignTransactionError(SoftKeySignatureErrorConstants.SIGN_TRANS_EMPTY_CHAIN_ID);
        }

        if (zswhqTransactionSignatureRequest.getSerializedTransaction().isEmpty()) {
            throw new SignTransactionError(SoftKeySignatureErrorConstants.SIGN_TRANS_EMPTY_TRANSACTION);
        }

        // Getting serializedTransaction and preparing signable transaction
        String serializedTransaction = zswhqTransactionSignatureRequest.getSerializedTransaction();

        // This is the un-hashed message which is used to recover public key
        byte[] message;

        // This is the hashed message which is signed.
        byte[] hashedMessage;

        try {
            message = Hex.decode(ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, zswhqTransactionSignatureRequest.getChainId()).toUpperCase());
            hashedMessage = Sha256Hash.hash(message);
        } catch (ZSWFormatterError eosFormatterError) {
            throw new SignTransactionError(String.format(SoftKeySignatureErrorConstants.SIGN_TRANS_PREPARE_SIGNABLE_TRANS_ERROR, serializedTransaction), eosFormatterError);
        }

        List<String> signatures = new ArrayList<>();

        for(String requestKey : zswhqTransactionSignatureRequest.getSigningPublicKeys()) {
            if (keyMap.containsKey(requestKey)) {
                byte[] rawD = (byte[]) keyMap.get(requestKey);
                try {
                    String signatureWithCheckSum =
                            Base58.encode(ZSWFormatter.addCheckSumToSignature(
                                    SM2Formatter.combineSignatureParts(
                                            ZSWFormatter.decodePublicKey(requestKey.substring(7), "PUB_GM_"),
                                            SM2Util.signDigest(
                                                    BCECUtil.convertPrivateKeyToParameters(SM2Util.getBCECPrivateKeyFromRawD(rawD)),
                                                    hashedMessage
                                            )
                                    ),

                                    "GM".getBytes()
                            ));
                    signatures.add("SIG_GM_".concat(signatureWithCheckSum));
                } catch (CryptoException error) {
                    throw new SignTransactionError(error);
                } catch (Base58ManipulationError error) {
                    throw new SignTransactionError(error);
                } catch (NoSuchAlgorithmException e) {
                    throw new SignTransactionError(e);
                } catch (InvalidKeySpecException e) {
                    throw new SignTransactionError(e);
                } catch (NoSuchProviderException e) {
                    throw new SignTransactionError(e);
                }
            }
        }
        return new ZswChainTransactionSignatureResponse(serializedTransaction, signatures, null);
    }

    /**
     * Gets available keys from signature provider <br> Check createSignatureRequest() flow in
     * "complete workflow" for more detail of how the method is used.
     * <p>
     * Public key of SM2 has only the "PUB_GM_" + [key] variant
     *
     * @return the available keys of signature provider in ZSW format
     */
    @Override
    public @NotNull List<String> getAvailableKeys() {
        return new ArrayList<String>(this.keyMap.keySet());

    }
}

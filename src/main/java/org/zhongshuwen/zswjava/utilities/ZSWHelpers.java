package org.zhongshuwen.zswjava.utilities;

import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.enums.AlgorithmEmployed;
import org.zhongshuwen.zswjava.error.utilities.Base58ManipulationError;
import org.zz.gmhelper.SM2Util;

import java.io.Serializable;

public class ZSWHelpers {

    public static String PrivateKeyToPublicKeyGM(String privateKeyGM) throws Base58ManipulationError {

        byte[] rawD = ZSWFormatter.decodePrivateKey(privateKeyGM.substring(7), AlgorithmEmployed.SM2P256V1);
        //BCECPrivateKey pKey = SM2Util.getBCECPrivateKeyFromRawD(rawD);
        return SM2Util.convertPrivateKeyRawDToZSWPublicKey(rawD);
    }
    public static ZSWAPIV1.KVPair<String, Object> CreateVariantFieldValue(@NotNull String key, @NotNull String type, Serializable value){
        ZSWAPIV1.KVPair<String,Object> o = new  ZSWAPIV1.KVPair<String,Object>();
        o.key = key;
        ZSWAPIV1.KVPair<String, Object> kvValue = new ZSWAPIV1.KVPair<String, Object>();
        kvValue.key = type;
        kvValue.value = value;
        o.value = kvValue;

        return o;
    }
}

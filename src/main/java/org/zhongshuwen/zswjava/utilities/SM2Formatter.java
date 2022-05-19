package org.zhongshuwen.zswjava.utilities;

import org.zhongshuwen.zswjava.error.signatureProvider.SignTransactionError;
import org.zz.gmhelper.BCECUtil;

public class SM2Formatter {
    public static byte[] combineSignatureParts(byte[] pubKeyDer, byte[] signature) throws ArrayIndexOutOfBoundsException {
        byte[] output = new byte[105];
        if((pubKeyDer.length+signature.length)>105){
            throw new ArrayIndexOutOfBoundsException("This signature is too long (public key + signature must be less than 105 bytes)!");
        }
        System.arraycopy(pubKeyDer, 0, output, 0, pubKeyDer.length);
        System.arraycopy(signature, 0, output, pubKeyDer.length, signature.length);
        return output;
    }
}

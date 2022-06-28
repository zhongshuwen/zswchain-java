package org.zz.gmhelper.test;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.Assert;
import org.junit.Test;
import org.zhongshuwen.zswjava.enums.AlgorithmEmployed;
import org.zhongshuwen.zswjava.utilities.ZSWFormatter;
import org.zz.gmhelper.BCECUtil;
import org.zz.gmhelper.SM2Util;

public class BCECUtilTest {

    @Test
    public void testECPrivateKeyPKCS8() {
        try {
            AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPairParameter();
            ECPrivateKeyParameters priKeyParams = (ECPrivateKeyParameters) keyPair.getPrivate();
            ECPublicKeyParameters pubKeyParams = (ECPublicKeyParameters) keyPair.getPublic();
            byte[] pkcs8Bytes = BCECUtil.convertECPrivateKeyToPKCS8(priKeyParams, pubKeyParams);
            BCECPrivateKey priKey = BCECUtil.convertPKCS8ToECPrivateKey(pkcs8Bytes);

            byte[] sign = SM2Util.sign(priKey, GMBaseTest.WITH_ID, GMBaseTest.SRC_DATA);
            System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2Util.verify(pubKeyParams, GMBaseTest.WITH_ID, GMBaseTest.SRC_DATA, sign);
            if (!flag) {
                Assert.fail("[withId] verify failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    /*
    @Test
    public void testZSWPrivateKey() {
        String privateKeyString = "PVT_GM_A7Q2R9XtHiWYTSeXUduJSLRu7aStg8qYF4deWechU3Vs5knrB";
        try {
            byte[] rawD = ZSWFormatter.decodePrivateKey(privateKeyString.substring(7), AlgorithmEmployed.SM2P256V1);
            BCECPrivateKey privateKey = SM2Util.getBCECPrivateKeyFromRawD(rawD);
            //String zswPublicKey = SM2Util.convertPrivateKeyRawDToZSWPublicKey(rawD);
            ECPublicKeyParameters publicKey = BCECUtil.buildECPublicKeyByPrivateKey(BCECUtil.convertPrivateKeyToParameters(privateKey));
            //BCECPublicKey pKey = SM2Util.convertPrivateKeyRawDToZSWPublicKey(rawD);

            byte[] sign = SM2Util.signDigest(privateKey,);
            System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2Util.verify(pubKeyParams, GMBaseTest.WITH_ID, GMBaseTest.SRC_DATA, sign);
            if (!flag) {
                Assert.fail("[withId] verify failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

     */
}

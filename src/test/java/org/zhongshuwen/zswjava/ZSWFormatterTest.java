package org.zhongshuwen.zswjava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.CharArrayReader;
import java.io.Reader;
import org.zhongshuwen.zswjava.error.ErrorConstants;
import org.zhongshuwen.zswjava.error.utilities.ZSWFormatterError;
import org.zhongshuwen.zswjava.utilities.ZSWFormatter;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Test;

public class ZSWFormatterTest {
    /*

    ===========================================
    Private Key PEM:
    -----BEGIN PRIVATE KEY-----
    MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgHzpHr6Bx7GTwwQ6+
    8q7UgicINktsqlVeX5ofejAZGfGgCgYIKoEcz1UBgi2hRANCAASFONOet2wndeyj
    wtBrGFYx3wCsZ8YTHBnRNNN7PRGk6pDDcY9bI9G7cGHEYmPRvBh9l87ITgBlEmxW
    I2youHxl
    -----END PRIVATE KEY-----
    Private Key Raw:
    1f3a47afa071ec64f0c10ebef2aed4822708364b6caa555e5f9a1f7a301919f1
    Private Key ZSW:
    PVT_GM_Ekfn4txwNBfy2oNrHn6EXZaBYoN5Tx5B2ELNuMpRSB6JNg7HP
    Private Key D: 1f3a47afa071ec64f0c10ebef2aed4822708364b6caa555e5f9a1f7a301919f1
    Private Key X: 8538d39eb76c2775eca3c2d06b185631df00ac67c6131c19d134d37b3d11a4ea
    Private Key Y: 90c3718f5b23d1bb7061c46263d1bc187d97cec84e0065126c56236ca8b87c65
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Public Key PEM:
    -----BEGIN PUBLIC KEY-----
    MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEhTjTnrdsJ3Xso8LQaxhWMd8ArGfG
    ExwZ0TTTez0RpOqQw3GPWyPRu3BhxGJj0bwYfZfOyE4AZRJsViNsqLh8ZQ==
    -----END PUBLIC KEY-----
    Public Key Raw:
    038538d39eb76c2775eca3c2d06b185631df00ac67c6131c19d134d37b3d11a4ea
    Public Key ZSW:
    PUB_GM_7quWLohZ9cdZb97VWKspnXwJ7RTMAxSGDmvmy7basQQDWTRH5W
    Public Key X: 8538d39eb76c2775eca3c2d06b185631df00ac67c6131c19d134d37b3d11a4ea
    Public Key Y: 90c3718f5b23d1bb7061c46263d1bc187d97cec84e0065126c56236ca8b87c65
    ===========================================

    */
    private static final String SM2_PRIVATE_KEY_1_PEM = "-----BEGIN EC PRIVATE KEY-----\n" +
            "MDECAQEEIB86R6+gcexk8MEOvvKu1IInCDZLbKpVXl+aH3owGRnxoAoGCCqBHM9VAYIt\n" +
            "-----END EC PRIVATE KEY-----";
    private static final String SM2_PRIVATE_KEY_1_ZSW = "PVT_GM_Ekfn4txwNBfy2oNrHn6EXZaBYoN5Tx5B2ELNuMpRSB6JNg7HP";
    //国密SM2 Private Key Test (ZSW to PEM)
    @Test
    public void validatePEMCreationOfSM2PrivateKey() {
        try {
            assertEquals(SM2_PRIVATE_KEY_1_PEM,
                    ZSWFormatter.convertZSWPrivateKeyToPEMFormat(SM2_PRIVATE_KEY_1_ZSW));
        } catch (ZSWFormatterError e) {
            fail(e.toString());
            //fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //国密SM2 Private Key Test (PEM to ZSW)
    @Test
    public void validateZSWCreationOfSM2PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(SM2_PRIVATE_KEY_1_ZSW,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(SM2_PRIVATE_KEY_1_PEM));
        } catch (ZSWFormatterError e) {
            fail(e.toString());
            //fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    /*
国密SM2 Private Key - Roundtrip Test (ZSW to PEM to ZSW)
Test uses output of one way conversion as input for return conversion.
 */
    @Test
    public void validateZSWtoPEMtoZSWCreationOfSM2PrivateKey() {

        try {
            String eosToPem = ZSWFormatter.convertZSWPrivateKeyToPEMFormat(SM2_PRIVATE_KEY_1_ZSW);
            assertEquals(SM2_PRIVATE_KEY_1_PEM,eosToPem);
            assertEquals(SM2_PRIVATE_KEY_1_ZSW,ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(SM2_PRIVATE_KEY_1_PEM));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }
    //SECP256R1 Private Key Test (ZSW to PEM)
    @Test
    public void validatePEMCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256R1 Private Key Test (PEM to ZSW)
    @Test
    public void validateZSWCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    /*
    SECP256R1 Private Key - Roundtrip Test (ZSW to PEM to ZSW)
    Test uses output of one way conversion as input for return conversion.
     */
    @Test
    public void validateZSWtoPEMtoZSWCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String eosToPem = ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            assertEquals(pemFormattedPrivateKey,eosToPem);
            assertEquals(eosFormattedPrivateKey,ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(eosToPem));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }



    //SECP256R1 Private Key Test (PEM to ZSW) - Invalid Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256r1PrivateKeyIsInvalidWrongHeader() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    //SECP256R1 Private Key Test (PEM to ZSW) - No Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256r1PrivateKeyIsInvalidNoHeader() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }



    //Validate PEM structure from SECP256R1 Private Key Test
    @Test
    public void validatePEMStructureOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String pemPrivateKey = ZSWFormatter
                    .convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            Reader reader = new CharArrayReader(pemPrivateKey.toCharArray());
            PemReader pemReader = new PemReader(reader);
            try {
                /*
                Validate that key type in PEM object is 'EC PRIVATE KEY'
                 */
                PemObject pemObject = pemReader.readPemObject();
                String type = pemObject.getType();
                assertEquals(type, "EC PRIVATE KEY");

            } catch (Exception e) {
                throw new ZSWFormatterError(e);
            }

        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError or other Exception to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (ZSW to PEM)
    @Test
    public void validatePEMCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to ZSW)
    @Test
    public void validateZSWCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    /*
SECP256K1 Private Key - Roundtrip Test (ZSW to PEM to ZSW)
Test uses output of one way conversion as input for return conversion.
 */
    @Test
    public void validateZSWtoPEMtoZSWCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String eosToPem = ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            assertEquals(pemFormattedPrivateKey,eosToPem);
            assertEquals(eosFormattedPrivateKey,ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(eosToPem));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }


    //SECP256K1 Private Key Test (PEM to ZSW) - Mixed case header is invalid
    @Test
    public void validateWhetherMixedCaseHeaderOfSecp256k1PrivateKeyIsValid() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----Begin EC Private Key-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expecting an ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        } catch (Exception e){
            fail("Expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to ZSW) - 5 dashes in header is required
    @Test
    public void validateWhether4DashesInHeaderOfSecp256k1PrivateKeyIsValid() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "----BEGIN EC PRIVATE KEY----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expecting an ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        } catch (Exception e){
            fail("Expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to ZSW) - Key data is required
    @Test
    public void validateWhetherKeyDataForSecp256k1PrivateKeyIsRequired() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "----BEGIN EC PRIVATE KEY----\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expecting an ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        } catch (Exception e){
            fail("Expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to ZSW) - Invalid Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256k1PrivateKeyIsInvalidWrongHeader() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        } catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to ZSW) - No Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256k1PrivateKeyIsInvalidNoHeader() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    ZSWFormatter.convertPEMFormattedPrivateKeyToZSWFormat(pemFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    //Passing a SECP256K1 Private Key as a SECP256R1 Private Key
    @Test
    public void validatePassingASecp256k1KeyAsSecp256r1Fails() {
        /*
        Using SECP256K1 key from above with private key prefix.
         */
        String eosFormattedPrivateKey = "PVT_R1_5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assertEquals(e.getCause().getLocalizedMessage(), ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),
                    ErrorConstants.BASE58_INVALID_CHECKSUM);
        } catch (Exception e) {
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Invalid Checksum Private Key Test
    @Test
    public void verifyInvalidChecksumThrowsExpectedError() {
        String eosFormattedPrivateKey = "4JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    ZSWFormatter.convertZSWPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assertEquals(e.getCause().getLocalizedMessage(), ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),
                    ErrorConstants.BASE58_INVALID_CHECKSUM);
        } catch (Exception e) {
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test (ZSW to PEM)
    @Test
    public void validatePEMCreationOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END PUBLIC KEY-----";

        try {
            assertEquals(pemFormattedPublicKey,
                    ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }


    //SECP256K1 Public Key Test (ZSW to PEM)
    @Test
    public void validatePEMCreationOfSecp256k1PublicKey() {
        String eosFormattedPublicKey = "PUB_K1_8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            assertEquals(pemFormattedPublicKey,
                    ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test (PEM to ZSW)
    @Test
    public void validateZSWCreationOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_8gHbKmPN7YXzYjLKguxABYvNqqxtBg8XJQ5M6ebKvupPsqugqj";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE8xOUetsCa8EfOlDEBAfREhJqspDoyEh6Szz2in47Tv5n52m9dLYyPCbqZkOB5nTSqtscpkQD/HpykCggvx09iQ==\n"
                + "-----END PUBLIC KEY-----";

        try {
            assertEquals(eosFormattedPublicKey,
                    ZSWFormatter.convertPEMFormattedPublicKeyToZSWFormat(pemFormattedPublicKey, false));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test - Roundtrip (ZSW to PEM to ZSW)
    @Test
    public void validateZSWtoPEMtoZSWConversionOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END PUBLIC KEY-----";

        try {
            String eosToPem = ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    ZSWFormatter.convertPEMFormattedPublicKeyToZSWFormat(eosToPem, false));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (ZSW to PEM to ZSW)
    @Test
    public void validateZSWtoPEMtoZSWConversionOfSecp256k1PublicKey() {
        String eosFormattedPublicKey = "PUB_K1_8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            String eosToPem = ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    ZSWFormatter.convertPEMFormattedPublicKeyToZSWFormat(eosToPem, false));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (ZSW to PEM to ZSW) Legacy SECP256K1
    @Test
    public void validateZSWtoPEMtoZSWConversionOfSecp256k1PublicKeyLegacy() {
        String eosFormattedPublicKey = "EOS5AzPqKAx4caCrRSAuyojY6rRKA3KJf4A1MY3paNVqV5eADEVm2";

        try {
            String eosToPem = ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(eosFormattedPublicKey,
                    ZSWFormatter.convertPEMFormattedPublicKeyToZSWFormat(eosToPem, true));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (ZSW to PEM to ZSW) Invalid ZSW throws error
    @Test
    public void validateZSWtoPEMtoZSWConversionOfAnInvalidSecp256k1PublicKeyThrowsError() {
        String eosFormattedPublicKey = "8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            String eosToPem = ZSWFormatter.convertZSWPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    ZSWFormatter.convertPEMFormattedPublicKeyToZSWFormat(eosToPem, false));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }

    /**
     * Validate positive test for PrepareSerializedTransactionForSigning
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String serializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String expectedSignableTransaction = chainId + serializedTransaction + Hex.toHexString(new byte[32]);

        try {
            String signableTransaction = ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId);
            assertEquals(expectedSignableTransaction, signableTransaction);
        } catch (ZSWFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Validate positive test for PrepareSerializedTransactionForSigning with ContextFreeData
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithContextFreeData() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String serializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String serializedContextFreeData = "c21bfb5ad4b64bfd04838b3b14f0ce0c7b92136cac69bfed41bef92f95a9bb20";
        String expectedSignableTransaction = chainId + serializedTransaction + serializedContextFreeData;

        try {
            String signableTransaction = ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, serializedContextFreeData);
            assertEquals(expectedSignableTransaction, signableTransaction);
        } catch (ZSWFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with invalid length input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning_thenThrowErrorLengthInput() {
        String chainId = "687fa513e18843ad3e820744f4ffcf9";
        String serializedTransaction = "8";
        String contextFreeData = "4";

        try {
            ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 129), eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with empty chainId
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithEmptyChainId_thenThrowErrorEmptyInput() {
        String chainId = "";
        String serializedTransaction = "someValue";
        String contextFreeData = "someValue";

        try {
            ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with empty serializedTransaction
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithEmptySerializedTransaction_thenThrowErrorEmptyInput() {
        String chainId = "someValue";
        String serializedTransaction = "";
        String contextFreeData = "someValue";

        try {
            ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with ContextFreeData with empty contextFreeData
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithContextFreeData_thenThrowErrorEmptyInput() {
        String chainId = "someValue";
        String serializedTransaction = "someValue";
        String contextFreeData = "";

        try {
            ZSWFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Validate positive ExtractSerializedTransactionFromSignable
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String expectedSerializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String signableTransaction = chainId + expectedSerializedTransaction + Hex.toHexString(new byte[32]);

        try {
            String serializedTransaction = ZSWFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            assertEquals(expectedSerializedTransaction, serializedTransaction);
        } catch (ZSWFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Validate positive ExtractSerializedTransactionFromSignable with Context Free Data
     */
    @Test
    public void validateExtractSerializedTransactionFromSignableWithContextFreeData() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String expectedSerializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String contextFreeData = "6595140530fcbd94469196e5e6d73af65693910df8fcf5d3088c3616bff5ee9f";
        String signableTransaction = chainId + expectedSerializedTransaction + contextFreeData;

        try {
            String serializedTransaction = ZSWFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            assertEquals(expectedSerializedTransaction, serializedTransaction);
        } catch (ZSWFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Negative test ExtractSerializedTransactionFromSignable with empty input
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowEmptyError() {
        String signableTransaction = "";

        try {
            ZSWFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, eosFormatterError.getMessage());
        }
    }

    /*
    Validate signature using a signable transaction and public key used to sign the transaction.
    Convert the signature to ZSW format.
     */

    @Test
    public void validateZSWSignatureCreationWithSECP256R1GeneratedPublicKey() {
        String publicKey = "PUB_R1_6Aze12hAmj1qWeXpdxsbMMP29NZ7EJhnuNJmDoBgx9xjmyZ8n8";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17528cab5c770a54cebec1000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323236000000000000c034000000000000a682102700000000000004454f530000000015426f6e757320666f7220676f6f64206a6f62212121000000000000000000000000000000000000000000000000000000000000000000";
        String derEncodedSignature = "304502202b180ef7236a62ff1e3fd741c3d5ba00cf3d3114a7e038a0730a2f45d1551219022100da335c840a4f42c051c12fed3d7d012bb083c7150c0eb691cac9ad9e898a75f3";
        String eosFormattedSignature = "SIG_R1_KaPKLBn1FnnYDf4E5zmnj7qQWWcN5REJFnadzLUyDp7TEVMAmD1CT15SyGmwdreoYTWSbJzWXayPdsHwLySWviiJoA7W4p";

        try {
            String pemPublicKey = ZSWFormatter.convertZSWPublicKeyToPEMFormat(publicKey);
            assertEquals(eosFormattedSignature, ZSWFormatter.convertDERSignatureToZSWFormat(Hex.decode(derEncodedSignature),Hex.decode(signableTransaction), pemPublicKey ));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    /*
    Fail while validating signature using a signable transaction and the wrong public key used to
    sign the transaction.
     */

    @Test
    public void validateZSWSignatureCreationWithWrongPublicKey() {
        String publicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17528cab5c770a54cebec1000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323236000000000000c034000000000000a682102700000000000004454f530000000015426f6e757320666f7220676f6f64206a6f62212121000000000000000000000000000000000000000000000000000000000000000000";
        String derEncodedSignature = "304502202b180ef7236a62ff1e3fd741c3d5ba00cf3d3114a7e038a0730a2f45d1551219022100da335c840a4f42c051c12fed3d7d012bb083c7150c0eb691cac9ad9e898a75f3";
        String eosFormattedSignature = "SIG_R1_KaPKLBn1FnnYDf4E5zmnj7qQWWcN5REJFnadzLUyDp7TEVMAmD1CT15SyGmwdreoYTWSbJzWXayPdsHwLySWviiJoA7W4p";

        try {
            String pemPublicKey = ZSWFormatter.convertZSWPublicKeyToPEMFormat(publicKey);
            assertEquals(eosFormattedSignature, ZSWFormatter.convertDERSignatureToZSWFormat(Hex.decode(derEncodedSignature),Hex.decode(signableTransaction), pemPublicKey ));
            fail("Expected ZSWFormatterError to be thrown!");
        }catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
            assertEquals("Could not recover public key from Signature.", e.getCause().getMessage());
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }
    }

    /*
    Validate signature using a r and s, signable transaction, and public key used to sign the transaction.
    Convert the signature to ZSW format.
     */

    @Test
    public void validateZSWSignatureCreationWithSECP256K1GeneratedPublicKey() {
        String publicKey = "-----BEGIN PUBLIC KEY-----\nMDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n-----END PUBLIC KEY-----";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad1714b9ab5cfb639ca4996b000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323225000000000000c034000000000000a682e80300000000000004454f5300000000046d656d6f000000000000000000000000000000000000000000000000000000000000000000";
        String rValue = "44170566286458861279997966394284345283382417819099318029887909824085364215455";
        String sValue = "5239695698990405032795389787700069207189254668737611240009161346294735197523";
        String eosFormattedSignature = "SIG_K1_KhXKyTi1h2D3LiX8bp4bhfjNQSaF61keisC6VcoHP16jbtHE4sAAzPTXHVAn3hPLUcvnMgG9bf5bZPvmpHBmQCA83VSz6Z";

        try {
            assertEquals(eosFormattedSignature, ZSWFormatter.convertRawRandSofSignatureToZSWFormat(rValue, sValue,Hex.decode(signableTransaction), publicKey ));
        } catch (ZSWFormatterError e) {
            fail("Not expecting an ZSWFormatterError to be thrown!");
        }

    }

    /*
    Verify failed validation of signature using a incorrect r and s, valid signable transaction, and valid public key used to sign the transaction.
    Changed r and s values slightly from positive test.
     */

    @Test
    public void eosSECP256K1SignatureValidationFailsWithIncorrectRorSvalue() {
        String publicKey = "-----BEGIN PUBLIC KEY-----\nMDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n-----END PUBLIC KEY-----";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad1714b9ab5cfb639ca4996b000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323225000000000000c034000000000000a682e80300000000000004454f5300000000046d656d6f000000000000000000000000000000000000000000000000000000000000000000";
        String rValue = "44170566286458861279997966394284345283382417819099318029887909824085364215450";
        String sValue = "5239695698990405032795389787700069207189254668737611240009161346294735197520";
        String eosFormattedSignature = "SIG_K1_KhXKyTi1h2D3LiX8bp4bhfjNQSaF61keisC6VcoHP16jbtHE4sAAzPTXHVAn3hPLUcvnMgG9bf5bZPvmpHBmQCA83VSz6Z";

        try {
            assertEquals(eosFormattedSignature, ZSWFormatter.convertRawRandSofSignatureToZSWFormat(rValue, sValue,Hex.decode(signableTransaction), publicKey ));
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError e) {
            assert(e instanceof ZSWFormatterError);
            assertEquals("Invalid point compression", e.getCause().getMessage());
        }catch (Exception e){
            fail("Expected ZSWFormatterError to be thrown!");
        }

    }


    /**
     * Negative test ExtractSerializedTransactionFromSignable with invalid length input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowLengthError() {
        String signableTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA30550000";

        try {
            ZSWFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            fail("Expected ZSWFormatterError to be thrown!");
        } catch (ZSWFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 129), eosFormatterError.getMessage());
        }
    }
}

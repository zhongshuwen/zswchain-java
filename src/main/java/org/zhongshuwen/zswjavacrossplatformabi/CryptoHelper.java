package org.zhongshuwen.zswjavacrossplatformabi;

import org.bitcoinj.core.Base58;

public class CryptoHelper {
    public static final int PUB_KEY_DATA_SIZE = 33;
    public static final int PRIV_KEY_DATA_SIZE = 32;
    public static final int SIGN_KEY_DATA_SIZE = 65;
    public static final int GM_SIGN_KEY_DATA_SIZE = 105;


    /// <summary>
    /// Convert encoded public key to byte array
    /// </summary>
    /// <param name="key">encoded public key</param>
    /// <param name="prefix">Optional prefix on key</param>
    /// <returns>public key bytes</returns>
    public static byte[] PubKeyStringToBytes(String key, String prefix) throws Exception {
        if(key.startsWith("PUB_R1_"))
        {
            return StringToKey(key.substring(7), PUB_KEY_DATA_SIZE, "R1");
        }else if(key.startsWith("PUB_K1_"))
        {
            return StringToKey(key.substring(7), PUB_KEY_DATA_SIZE, "K1");
        }else if(key.startsWith("PUB_GM_"))
        {
            return StringToKey(key.substring(7), PUB_KEY_DATA_SIZE, "GM");
        }
        else if(key.startsWith(prefix))
        {
            return StringToKey(key.substring(prefix.length()), PUB_KEY_DATA_SIZE, prefix);
        }
        else
        {
            throw new Exception("unrecognized public key format.");
        }
    }

    public static byte[] PubKeyStringToBytes(String key) throws Exception {
        return PubKeyStringToBytes(key, "EOS");
    }
    public static byte[] PubKeyStringToBytesNoChecksum(String key) throws Exception
    {
        byte[] result = PubKeyStringToBytes(key);

        byte[] output = new byte[result.length-4];
        System.arraycopy(result,0,output, 0, output.length);
        return output;
    }
    /// <summary>
    /// Convert encoded public key to byte array
    /// </summary>
    /// <param name="key">encoded public key</param>
    /// <param name="prefix">Optional prefix on key</param>
    /// <returns>public key bytes</returns>
    public static byte[] PrivKeyStringToBytes(String key)
    {
        if (key.startsWith("PVT_R1_"))
            return StringToKey(key.substring(7), PRIV_KEY_DATA_SIZE, "R1");
        else if (key.startsWith("PVT_K1_"))
            return StringToKey(key.substring(7), PRIV_KEY_DATA_SIZE, "K1");
        else if (key.startsWith("PVT_GM_"))
            return StringToKey(key.substring(7), PRIV_KEY_DATA_SIZE, "GM");
        else
            return StringToKey(key, PRIV_KEY_DATA_SIZE, "sha256x2");
    }
    public static byte[] PrivKeyStringToBytesNoChecksum(String key) throws Exception
    {
        byte[] result = PrivKeyStringToBytes(key);

        byte[] output = new byte[result.length-4];
        System.arraycopy(result,0,output, 0, output.length);
        return output;
    }

    /// <summary>
    /// Convert encoded signature to byte array
    /// </summary>
    /// <param name="sign">encoded signature</param>
    /// <returns>signature bytes</returns>
    public static byte[] SignStringToBytes(String sign, boolean padOut) throws Exception {
        if (sign.startsWith("SIG_K1_"))
            return StringToKey(sign.substring(7), SIGN_KEY_DATA_SIZE, "K1");
        if (sign.startsWith("SIG_R1_"))
            return StringToKey(sign.substring(7), SIGN_KEY_DATA_SIZE, "R1");
        if (sign.startsWith("SIG_GM_")) {
            if(padOut){
                byte[] base = StringToKey(sign.substring(7), SIGN_KEY_DATA_SIZE, "GM");
                if(base.length!=GM_SIGN_KEY_DATA_SIZE){
                    byte[] full = new byte[GM_SIGN_KEY_DATA_SIZE];
                    System.arraycopy(base,0,full, 0, GM_SIGN_KEY_DATA_SIZE);
                }else{
                    return base;
                }

            }
            return StringToKey(sign.substring(7), SIGN_KEY_DATA_SIZE, "GM");
        }else
            throw new Exception("unrecognized signature format.");
    }
    public static byte[] SignStringToBytesNoChecksum(String sign, boolean padOut) throws Exception
    {
        byte[] result = SignStringToBytes(sign, padOut);

        byte[] output = new byte[result.length-4];
        System.arraycopy(result,0,output, 0, output.length);
        return output;
    }


    /// <summary>
    /// Convert encoded signature to byte array
    /// </summary>
    /// <param name="sign">encoded signature</param>
    /// <returns>signature bytes</returns>
    public static byte[] SignStringToBytes(String sign) throws Exception {
        return SignStringToBytes(sign, false);
    }
    /// <summary>
    /// Convert Pub/Priv key or signature to byte array
    /// </summary>
    /// <param name="key">generic key</param>
    /// <param name="size">Key size</param>
    /// <param name="keyType">Optional key type. (sha256x2, R1, K1)</param>
    /// <returns>key bytes</returns>
    public static byte[] StringToKey(String key, int size, String keyType)
    {
        byte[] keyBytes = Base58.decode(key);
        return keyBytes;
    }


    //start bytes to string
}

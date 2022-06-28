package org.zhongshuwen.zswjava.utilities;

import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;

public class ZSWIdHelper {
    public static final String ZERO_PADS_UINT128 = "00000000000000000000000000000000";


    public static String getZSWIdFromUUID(String uuid){
        BigInteger bi = new BigInteger(""+(uuid.replaceAll("-","")), 16);
        return bi + "";
    }
    private static long positiveByte(byte x){
        return (x+0x100L)&0xffL;
    }
    public static String getZSW40BitIdFromUUID(String uuid){
        byte[] decode = Hex.decode(uuid.replaceAll("-","").substring(22,32));
        long v = (positiveByte(decode[0])<<32L) |(positiveByte(decode[1])<<24L) |(positiveByte(decode[2])<<16L) |(positiveByte(decode[3])<<8L) |(positiveByte(decode[4]));
        return v+"";
    }
    public static String getZSW64BitIdFromUUID(String uuid){
        return (new BigInteger(uuid.replaceAll("-","").substring(16,32),16)).toString();
    }
    public static String getUUIDFromUint128String(String uint128String){
        String hex = ZERO_PADS_UINT128 + (new BigInteger(uint128String).toString(16));
        String p = hex.substring(hex.length()-32);
        return p.substring(0, 8)+ "-" +p.substring(8, 12)+ "-" +p.substring(12, 16)+ "-" +p.substring(16, 20)+ "-" +p.substring(20, 32);


    }

}

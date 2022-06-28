package org.zhongshuwen.zswjavacrossplatformabi;

import com.google.gson.internal.LinkedTreeMap;
import org.bitcoinj.core.Base58;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.zhongshuwen.zswjava.abitypes.Symbol;
import org.zhongshuwen.zswjava.abitypes.ZSWExtendedAsset;
import org.zhongshuwen.zswjava.enums.AlgorithmEmployed;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeError;
import org.zhongshuwen.zswjava.error.utilities.Base58ManipulationError;
import org.zhongshuwen.zswjava.utilities.SM2Formatter;
import org.zhongshuwen.zswjava.utilities.ZSWFormatter;
import org.zz.gmhelper.BCECUtil;
import org.zz.gmhelper.SM2Util;

import java.nio.ByteBuffer;


public class ZSWReadStream {

    public ByteBuffer bb;

    public ZSWReadStream(byte[] data) {
        bb = ByteBuffer.wrap(data);
    }

    public int GetUnsignedByte(byte b){
        return (0x100+(int)b)&0xff;
    }

    public Object ReadByte()
    {
        return GetUnsignedByte(bb.get());
    }

    public Object ReadUint16()
    {
        return (GetUnsignedByte(bb.get())&0xff)|((((GetUnsignedByte(bb.get())&0xff)<<8)));
    }
    public static long ByteLongUnsigned(byte b){
        return (0x100L+(long)b)&0xffL;
    }
    public Object ReadUint32()
    {
        long a =ByteLongUnsigned(bb.get())|((long)(ByteLongUnsigned(bb.get())<<8L))|(ByteLongUnsigned(bb.get())<<16L)|(ByteLongUnsigned(bb.get())<<24L);
        //long x = (bb.getInt()&0xffffffffL);

        return a;// (long)(x);
    }

    public Object ReadInt64()
    {
        return bb.getLong();
    }

    public Object ReadUint64()
    {
        return (bb.getLong()&0xffffffffffffffffL);
    }

    public Object ReadInt128()
    {
        byte[] data = new byte[16];
        bb.get(data);
        return SerializationHelper.SignedBinaryToDecimal(data);
    }

    public Object ReadUInt128()
    {
        byte[] data = new byte[16];
        bb.get(data);
        return SerializationHelper.BinaryToDecimal(data);
    }

    public Object ReadVarUint32()
    {
        long v = 0;
        int bit = 0;
        while (true)
        {
            long b = (long)(bb.get());
            v |= (long)((b & 0x7f) << bit);
            bit += 7;
            if ((b & 0x80) == 0)
                break;
        }
        return v >> 0;
    }

    public Object ReadVarInt32()
    {
        long v = (long)ReadVarUint32();

        if ((v & 1) != 0)
            return ((~v) >> 1) | 0x80000000;
        else
            return v >> 1;
    }

    public Object ReadFloat32()
    {
        return bb.getFloat();
    }

    public Object ReadFloat64()
    {
        return bb.getDouble();
    }
    public String ReadHexString(int numberOfBytes) {
        byte[] bytes = new byte[numberOfBytes];
        bb.get(bytes);
        return ByteUtils.toHexString(bytes).toUpperCase();
    }
    public Object ReadFloat128()
    {
        return ReadHexString(16);
    }

    public Object ReadBytes()
    {
        int size = (int)(((long)ReadVarUint32())&0xffffffffL);
        return ReadHexString(size);
        /*
        byte[] bytes = new byte[size];
        bb.get(bytes);
        return bytes;

         */
    }

    public Object ReadBool()
    {
        return bb.get() == 1;
    }

    public Object ReadString()
    {
        int size = (int)(((long)ReadVarUint32())&0xffffffffL);
        if(size > 0){
            byte[] bytes = new byte[size];
            bb.get(bytes);
            try {
                return new String(bytes, "UTF-8");
            }catch(Exception ex){
                return null;
            }
        }else{
            return null;
        }
    }

    public Object ReadName()
    {
        byte[] a = new byte[8];
        bb.get(a);
        String result = "";


        for (int bit = 63; bit >= 0;)
        {
            int c = 0;
            for (int i = 0; i < 5; ++i)
            {
                if (bit >= 0)
                {
                    c = (c << 1) | ((a[(int)Math.floor((double)bit / 8)] >> (bit % 8)) & 1);
                    --bit;
                }
            }
            if (c >= 6)
                result += (char)(c + 'a' - 6);
            else if (c >= 1)
                result += (char)(c + '1' - 1);
            else
                result += '.';
        }

        if (result.equals("............."))
            return result;

        while (result.endsWith("."))
            result = result.substring(0, result.length() - 1);

        return result;
    }

    public Object ReadAsset()
    {
        byte[] amount = new byte[8];
        bb.get(amount);
        Symbol symbol =  (Symbol)ReadSymbol();
        String s = SerializationHelper.SignedBinaryToDecimal(amount, symbol.precision+1);

        if (symbol.precision > 0)
            s = s.substring(0, s.length() - symbol.precision) + '.' + s.substring(s.length() - symbol.precision);

        return s + " " + symbol.name;
    }

    public Object ReadTimePoint()
    {
        long low = (long)ReadUint32();
        long high = (long)ReadUint32();
        return SerializationHelper.TimePointToDate((high >> 0) * 0x100000000L + (low >> 0));
    }

    public Object ReadTimePointSec()
    {
        long secs = ((long)ReadUint32())&0xffffffffL;
        return SerializationHelper.TimePointSecToDate(secs);
    }

    public Object ReadBlockTimestampType()
    {
        long slot = (long)ReadUint32();
        return SerializationHelper.BlockTimestampToDate(slot);
    }

    public Object ReadSymbolString()
    {
        Symbol value = (Symbol)ReadSymbol();
        return value.precision + "," + value.name;
    }

    public Object ReadSymbolCode()
    {
        byte[] a = new byte[8];
        bb.get(a);
        StringBuilder sb = new StringBuilder();


        int len;
        for (len = 0; len < a.length; ++len)
            if (a[len] == 0)
                break;
            else
                sb.append((char)(((int)a[len])&0xffffffff));

        return sb.toString();
    }

    public Object ReadChecksum160()
    {
        return ReadHexString(20);
    }

    public Object ReadChecksum256()
    {
        return ReadHexString(32);
    }

    public Object ReadChecksum512()
    {
        return ReadHexString(64);
    }

    public Object ReadPublicKey() throws DeserializeError {
        int type = ((int)bb.get())&0xff;

        try {
            if (type == ZSWWriteStream.KeyType.k1) {
                byte[] bytes = new byte[CryptoHelper.PUB_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePublicKey(bytes, AlgorithmEmployed.SECP256K1, false);
            }else if(type == ZSWWriteStream.KeyType.r1){
                byte[] bytes = new byte[CryptoHelper.PUB_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePublicKey(bytes, AlgorithmEmployed.SECP256R1, false);
            }else if(type == ZSWWriteStream.KeyType.wa){
                //todo: add webauthn public key deserialization support
                throw new DeserializeError("Deserialize Webauthn Public Key is not yet supported!");
            }else if(type == ZSWWriteStream.KeyType.gm){
                byte[] bytes = new byte[CryptoHelper.PUB_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePublicKey(bytes, AlgorithmEmployed.SM2P256V1, false);
            }else{
                throw new DeserializeError("unsupported public key type for deserialization");
            }
        }catch(Base58ManipulationError b58Err){
            throw new DeserializeError(b58Err+"");
        }
    }

    public Object ReadPrivateKey() throws DeserializeError {

        int type = ((int)bb.get())&0xff;

        try {
            if (type == ZSWWriteStream.KeyType.k1) {
                byte[] bytes = new byte[CryptoHelper.PRIV_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePrivateKey(bytes, AlgorithmEmployed.SECP256K1);
            }else if(type == ZSWWriteStream.KeyType.r1){
                byte[] bytes = new byte[CryptoHelper.PRIV_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePrivateKey(bytes, AlgorithmEmployed.SECP256R1);
            }else if(type == ZSWWriteStream.KeyType.gm){
                byte[] bytes = new byte[CryptoHelper.PRIV_KEY_DATA_SIZE];
                bb.get(bytes);
                return ZSWFormatter.encodePrivateKey(bytes, AlgorithmEmployed.SM2P256V1);
            }else{
                throw new DeserializeError("unsupported private key type for deserialization");
            }
        }catch(Base58ManipulationError b58Err){
            throw new DeserializeError(b58Err+"");
        }
    }

    public Object ReadSignature() throws DeserializeError
    {
        int type = ((int)bb.get())&0xff;


        if (type == ZSWWriteStream.KeyType.k1) {
            byte[] bytes = new byte[CryptoHelper.SIGN_KEY_DATA_SIZE];
            bb.get(bytes);
            return "SIG_K1_"+Base58.encode(ZSWFormatter.addCheckSumToSignature(
                    bytes,

                    "K1".getBytes()
            ));
        }else if(type == ZSWWriteStream.KeyType.r1){
            byte[] bytes = new byte[CryptoHelper.SIGN_KEY_DATA_SIZE];
            bb.get(bytes);
            return "SIG_R1_"+Base58.encode(ZSWFormatter.addCheckSumToSignature(
                    bytes,

                    "R1".getBytes()
            ));
        }else if(type == ZSWWriteStream.KeyType.wa){
            //todo: add webauthn signature key deserialization support
            throw new DeserializeError("Deserialize Webauthn Public Key is not yet supported!");
        }else if(type == ZSWWriteStream.KeyType.gm){
            byte[] bytes = new byte[CryptoHelper.GM_SIGN_KEY_DATA_SIZE];
            bb.get(bytes);
            return "SIG_GM_"+Base58.encode(ZSWFormatter.addCheckSumToSignature(
                    bytes,

                    "GM".getBytes()
            ));
        }else{
            throw new DeserializeError("unsupported signature type for deserialization");
        }
    }

    public Object ReadExtendedAsset()
    {
        return new ZSWExtendedAsset((String)ReadAsset(), (String)ReadName());

    }

    public Object ReadSymbol()
    {


        byte precision = (byte)bb.get();
        StringBuilder sb = new StringBuilder();
        boolean isFinished = false;
        for(int i=0;i<7;i++){
            byte nc = (byte)(bb.get());
            if(nc == 0){
                isFinished = true;
            }else if(!isFinished){
                sb.append((char)(((int)nc)&0xff));
            }
        }
        return new Symbol(sb.toString(), precision);
    }
}

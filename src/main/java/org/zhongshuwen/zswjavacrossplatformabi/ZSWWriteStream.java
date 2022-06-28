package org.zhongshuwen.zswjavacrossplatformabi;

import com.google.gson.internal.LinkedTreeMap;
import org.firebirdsql.decimal.Decimal128;
import org.zhongshuwen.zswjava.abitypes.*;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializeError;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Dictionary;
import java.util.Map;

public class ZSWWriteStream  {

    public final class KeyType {

        public static final int k1 = 0;
        public static final int r1 = 1;
        public static final int wa = 2;
        public static final int gm = 3;
    }
    public ByteBuffer bb;
    public ZSWWriteStream (int capacity) {
        bb = ByteBuffer.allocate(capacity);
    }

    public void WriteByte(byte value)
    {
        bb.put(value);
    }

    public void WriteUint16(int value)
    {
        bb.put((byte)(value&0xff));
        bb.put((byte)((value>>8)&0xff));
        
    }
    public void WriteNullT(){
        bb.put((byte)0);
    }
    public void WriteAnyVar(Object value) throws Exception {
        if(value == null){
            WriteNullT();

        }else if(value instanceof String){

        }
         if(value instanceof LinkedTreeMap || value instanceof Dictionary) {
             String[] fields = ObjectReader.getFieldList(value);
             for (String f : fields) {

                 Object fValue = ObjectReader.getField(value, f);
                 if (fValue == null) {

                 }
             }
         }

    }

    public void WriteUint32(long value)
    {

        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putLong(value);
        bb.put(bytes[7]);
        bb.put(bytes[6]);
        bb.put(bytes[5]);
        bb.put(bytes[4]);

        /*
        bb.put(bytes[4]);
        bb.put(bytes[5]);
        bb.put(bytes[6]);
        bb.put(bytes[7]);

         */
    }
    public void WriteUint32Reversed(long value)
    {

        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putLong(value);

        bb.put(bytes[4]);
        bb.put(bytes[5]);
        bb.put(bytes[6]);
        bb.put(bytes[7]);
    }

    public void WriteInt64(long value) throws Exception {
        bb.put(SerializationHelper.SignedDecimalToBinary(8, Long.toString(value)));
    }

    public void WriteUint64(long value) throws Exception {
        bb.put(SerializationHelper.DecimalToBinary(8, Long.toUnsignedString(value)));
    }

    public void WriteInt128(BigInteger value) throws Exception {
        bb.put(SerializationHelper.SignedDecimalToBinary(16, value.toString()));
    }

    public void WriteUInt128(BigInteger value) throws Exception {
        bb.put(SerializationHelper.DecimalToBinary(16, value.toString()));
    }

    public void WriteVarUint32(long value)
    {
        long v = value & 0xffffffffL;
        while (true)
        {
            if ((v >> 7) != 0)
            {
                bb.put((byte)(0x80 | (v & 0x7f)));
                v >>= 7;
            }
            else
            {
                bb.put((byte)v);
                break;
            }
        }
    }

    public void WriteVarInt32(int value)
    {
        long v = value;
        WriteVarUint32((v << 1) ^ (v >> 31));
    }

    public void WriteFloat32(float value)
    {
        bb.putFloat(value);
    }

    public void WriteFloat64(double value)
    {
        bb.putDouble(value);
    }

    public void WriteFloat128(Decimal128 value)
    {
        bb.put(value.toBytes());
    }

    public void WriteBytes(byte[] bytes)
    {

        WriteVarUint32(bytes.length);
        bb.put(bytes);
    }

    public void WriteBool(boolean value)
    {
        bb.put((byte)(value?1:0));
    }

    public void WriteString(String value)
    {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);


        WriteVarUint32((long)bytes.length);
        if(bytes.length>0){
            bb.put(bytes);
        }
    }

    public void WriteName(String value)
    {
        bb.put(SerializationHelper.ConvertNameToBytes(value));
    }

    public void WriteAsset(String value) throws Exception {
        String s = (value).trim();
        int pos = 0;
        String amount = "";
        byte precision = 0;

        if (s.charAt(pos) == '-')
        {
            amount += '-';
            ++pos;
        }

        boolean foundDigit = false;
        while (pos < s.length() && s.charAt(pos) >= '0' && s.charAt(pos) <= '9')
        {
            foundDigit = true;
            amount += s.charAt(pos);
            ++pos;
        }

        if (!foundDigit)
            throw new Exception("Asset must begin with a number");

        if (s.charAt(pos) == '.')
        {
            ++pos;
            while (pos < s.length() && s.charAt(pos) >= '0' && s.charAt(pos) <= '9')
            {
                amount += s.charAt(pos);
                ++precision;
                ++pos;
            }
        }
        String name = s.substring(pos).trim();

        bb.put(SerializationHelper.SignedDecimalToBinary(8, amount));
        WriteSymbol(new Symbol(name, precision));
    }

    public void WriteTimePoint(Date value)
    {
        WriteUint32((value.getTime() & 0xffffffff));
        WriteUint32(value.getTime()>>32);
    }

    public void WriteTimePointSec(Date value)
    {
        long v = SerializationHelper.DateToTimePointSec(value);
        WriteUint32(v&0xffffffffL);
    }

    public void WriteBlockTimestampType(Date value)
    {
        WriteUint32(SerializationHelper.DateToBlockTimestamp(value));
    }

    public void WriteSymbolString(String value) throws Exception {
        String[] parts = value.split(",");

        if(parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0 || !Character.isDigit(parts[0].charAt(0)) ||  !Character.isLetter(parts[1].charAt(0)) || !Character.isUpperCase(parts[1].charAt(0))){
            throw new Exception("Invalid symbol.");
        }
        WriteSymbol(new Symbol(parts[1], Byte.parseByte(parts[0])));
    }

    public void WriteSymbolCode(String value)
    {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        if (bytes.length >= 8)
            bb.put(bytes, 0, 8);
        else
        {
            bb.put(bytes, 0, bytes.length);
            int extra = 8-bytes.length;
            while(extra>0){
                extra--;
                bb.put((byte)0);
            }
        }
    }

    public void WriteChecksum160(String value) throws Exception {
        byte[] bytes = SerializationHelper.HexStringToByteArray(value);

        if (bytes.length != 20)
            throw new Exception("Binary data has incorrect size");
        bb.put(bytes);
    }

    public void WriteChecksum256(String value) throws Exception {
        byte[] bytes = SerializationHelper.HexStringToByteArray(value);

        if (bytes.length != 32)
            throw new Exception("Binary data has incorrect size");

        bb.put(bytes);
    }

    public void WriteChecksum512(String value) throws Exception {
        byte[] bytes = SerializationHelper.HexStringToByteArray(value);

        if (bytes.length != 64)
            throw new Exception("Binary data has incorrect size");

        bb.put(bytes);
    }
    public int GetKeyType(String key) throws Exception {
        if(key.startsWith("EOS")){
            return KeyType.k1;
        }
        String keyTypeString = key.substring(4,6);
        if(keyTypeString.equals("K1")){
            return KeyType.k1;
        }else if(keyTypeString.equals("R1")){
            return KeyType.r1;
        }else if(keyTypeString.equals("WA")){
            return KeyType.wa;
        }else if(keyTypeString.equals("GM")){
            return KeyType.gm;
        }else{
            throw new Exception("Unknown key type or malformed key");
        }
    }


    public void WritePublicKey(String value) throws Exception {
        bb.put((byte)GetKeyType(value));
        bb.put(CryptoHelper.PubKeyStringToBytes(value));
    }

    public void WritePrivateKey(String value) throws Exception {
        bb.put((byte)GetKeyType(value));
        bb.put(CryptoHelper.PrivKeyStringToBytes(value));
    }

    public void WriteSignature(String value) throws Exception {
        bb.put((byte)GetKeyType(value));
        bb.put(CryptoHelper.SignStringToBytesNoChecksum(value, true));
    }

    public void WriteExtendedAsset(ZSWExtendedAsset value) throws Exception {
        WriteAsset(value.quantity);
        WriteName(value.contract);
    }

    public void WriteSymbol(Symbol value)
    {

        WriteByte(value.precision);
        byte[] bytes = value.name.getBytes(StandardCharsets.UTF_8);

        if (bytes.length >= 7)
            bb.put(bytes, 0, 7);
        else
        {
            bb.put(bytes);
            int extra = 7-bytes.length;
            while(extra>0){
                extra--;
                bb.put((byte)0);
            }
        }
    }

    public void WriteExtension(ZSWV1Extension extension)
    {
        if (extension.data == null)
            return;

        WriteUint16(extension.type);
        WriteBytes(extension.data);
    }

    public void WritePermissionLevel(PermissionLevel perm)
    {
        WriteName(perm.actor);
        WriteName(perm.permission);
    }


}

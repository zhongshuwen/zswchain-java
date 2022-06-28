package org.zhongshuwen.zswjavacrossplatformabi;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import com.google.common.base.CaseFormat;

import org.bouncycastle.util.encoders.Base64;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;

import java.lang.Character;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerializationHelper {
    /// <summary>
    /// Is a big Number negative
    /// </summary>
    /// <param name="bin">big number in byte array</param>
    /// <returns></returns>
    public static boolean IsNegative(byte[] bin)
    {
        return (bin[bin.length - 1] & 0x80) != 0;
    }

    /// <summary>
    /// Negate a big number
    /// </summary>
    /// <param name="bin">big number in byte array</param>
    public static void Negate(byte[] bin)
    {
        int carry = 1;
        for (int i = 0; i < bin.length; ++i)
        {
            int x = (~bin[i] & 0xff) + carry;
            bin[i] = (byte)x;
            carry = x >> 8;
        }
    }

    /// <summary>
    /// Convert an unsigned decimal number as String to a big number
    /// </summary>
    /// <param name="size">Size in bytes of the big number</param>
    /// <param name="s">decimal encoded as string</param>
    /// <returns></returns>
    public static byte[] DecimalToBinary(int size, String s) throws Exception {

            int[] result = new int[size];

            for (int i = 0; i < s.length(); ++i)
            {
                int srcDigit = s.charAt(i);

                if (srcDigit < 0x30 || srcDigit > 0x39)
                    throw new Error("invalid number");
                int carry = srcDigit - 0x30;
                for (int j = 0; j < size; ++j)
                {
                    int x = result[j] * 10 + carry;
                    result[j] = (x&0xff);
                    carry = x >> 8;
                }
                if (carry != 0)
                    throw new Error("number is out of range");
            }
            byte[] bResult = new byte[size];
            for(int i=0;i<size;i++){
                bResult[i] = (byte)((0x100+result[i])&0xff);
            }
            return bResult;

        /*
        byte[] result = new byte[size];
        for (int i = 0; i < s.length(); ++i)
        {
            char srcDigit = s.charAt(i);
            if (srcDigit < '0' || srcDigit > '9')
                throw new Exception("invalid number");
            int carry = srcDigit - '0';
            for (int j = 0; j < size; ++j)
            {
                int x = result[j] * 10 + carry;
                result[j] = (byte)x;
                carry = x >> 8;
            }
            if (carry != 0)
                throw new Exception("number is out of range");
        }
        return result;

         */
    }

    /// <summary>
    /// Convert an signed decimal number as String to a big number
    /// </summary>
    /// <param name="size">Size in bytes of the big number</param>
    /// <param name="s">decimal encoded as string</param>
    /// <returns></returns>
    public static byte[] SignedDecimalToBinary(int size, String s) throws Exception {
        boolean negative = s.charAt(0) == '-';
        if (negative)
            s = s.substring(1,s.length());
            //s = s.Substring(0, 1);
        byte[] result = DecimalToBinary(size, s);
        if (negative)
            Negate(result);
        return result;
    }

    /// <summary>
    /// Convert big number to an unsigned decimal number
    /// </summary>
    /// <param name="bin">big number as byte array</param>
    /// <param name="minDigits">0-pad result to this many digits</param>
    /// <returns></returns>
    public static String BinaryToDecimal(byte[] bin, int minDigits)
    {
        List result = new ArrayList<Character>(minDigits);

        for (int i = 0; i < minDigits; i++)
        {
            result.add('0');
        }

        for (int i = bin.length - 1; i >= 0; --i)
        {
            int carry = (((int)bin[i])+0x100)&0xff;
            for (int j = 0; j < result.size(); ++j)
            {
                int x = ((((char)result.get(j)) - '0') << 8) + carry;
                result.set(j , (char)('0' + (x % 10)));
                carry = (x / 10) | 0;
            }
            while (carry != 0)
            {
                result.add((char)('0' + carry % 10));
                carry = (carry / 10) | 0;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=result.size()-1;i>=0;i--){
            sb.append((char)result.get(i));
        }
        return sb.toString();
    }
    public static String BinaryToDecimal(byte[] bin)
    {
        return BinaryToDecimal(bin, 1);

    }

    /// <summary>
    /// Convert big number to an signed decimal number
    /// </summary>
    /// <param name="bin">big number as byte array</param>
    /// <param name="minDigits">0-pad result to this many digits</param>
    /// <returns></returns>
    public static String SignedBinaryToDecimal(byte[] bin, int minDigits)
    {
        if (IsNegative(bin))
        {
            Negate(bin);
            return '-' + BinaryToDecimal(bin, minDigits);
        }
        return BinaryToDecimal(bin, minDigits);
    }
    public static String SignedBinaryToDecimal(byte[] bin)
    {
        if (IsNegative(bin))
        {
            Negate(bin);
            return '-' + BinaryToDecimal(bin, 1);
        }
        return BinaryToDecimal(bin, 1);
    }

    /// <summary>
    /// Convert base64 with fc prefix to byte array
    /// </summary>
    /// <param name="s">string to convert</param>
    /// <returns></returns>
    public static byte[] Base64FcStringToByteArray(String s)
    {
        //fc adds extra '='
        if((s.length() & 3) == 1 && s.charAt(s.length()-1)== '=')
        {
            return Base64.decode(s.substring(0, s.length()-1));

        }

        return Base64.decode(s);
    }

    /// <summary>
    /// Convert ascii char to symbol value
    /// </summary>
    /// <param name="c"></param>
    /// <returns></returns>
    public static byte CharToSymbol(char c)
    {
        if (c >= 'a' && c <= 'z')
            return (byte)(c - 'a' + 6);
        if (c >= '1' && c <= '5')
            return (byte)(c - '1' + 1);
        return 0;
    }

    /// <summary>
    /// Convert snake case String to pascal case
    /// </summary>
    /// <param name="s">string to convert</param>
    /// <returns></returns>
    public static String SnakeCaseToPascalCase(String s)
    {
        if (s == null )
        {
            return s;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s);
    }

    /// <summary>
    /// Convert pascal case String to snake case
    /// </summary>
    /// <param name="s">string to convert</param>
    /// <returns></returns>
    public static String PascalCaseToSnakeCase(String s)
    {
        if (s == null )
        {
            return s;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);

    }
/*
    /// <summary>
    /// Serialize object to byte array
    /// </summary>
    /// <param name="obj">object to serialize</param>
    /// <returns></returns>
    public static byte[] ObjectToByteArray(Object obj)
    {
        if (obj == null)
            return null;

        BinaryFormatter bf = new BinaryFormatter();
        using (MemoryStream ms = new MemoryStream())
        {
            bf.Serialize(ms, obj);
            return ms.ToArray();
        }
    }

 */

    /// <summary>
    /// Encode byte array to hexadecimal string
    /// </summary>
    /// <param name="ba">byte array to convert</param>
    /// <returns></returns>
    public static String ByteArrayToHexString(byte[] ba)
    {
        return ByteUtils.toHexString(ba);
    }

    /// <summary>
    /// Decode hexadecimal String to byte array
    /// </summary>
    /// <param name="hex"></param>
    /// <returns></returns>
    public static byte[] HexStringToByteArray(String hex)
    {
        return ByteUtils.fromHexString(hex);
    }
/*
    /// <summary>
    /// Serialize object to hexadecimal encoded string
    /// </summary>
    /// <param name="obj"></param>
    /// <returns></returns>
    public static String ObjectToHexString(Object obj)
    {
        return ByteArrayToHexString(ObjectToByteArray(obj));
    }
*/
    public static int CombinedListLength(List<byte[]> lists){
        int length = 0;
        for(byte[] l : lists){
            if(l != null){
                length += l.length;
            }
        }
        return length;
    }

    /// <summary>
    /// Combina multiple arrays into one
    /// </summary>
    /// <param name="arrays"></param>
    /// <returns></returns>
    public static byte[] Combine(List<byte[]> arrays)
    {
        byte[] ret = new byte[CombinedListLength(arrays)];
        int offset = 0;
        for (byte[] data : arrays)
        {
            if (data == null) continue;

            System.arraycopy(data, 0, ret, offset, data.length);

            offset += data.length;
        }
        return ret;
    }

    /// <summary>
    /// Convert DateTime to `time_point` (miliseconds since epoch)
    /// </summary>
    /// <param name="value">date to convert</param>
    /// <returns></returns>
    public static long DateToTimePoint(Date value)
    {
        return value.getTime();
    }

    /// <summary>
    /// Convert `time_point` (miliseconds since epoch) to DateTime
    /// </summary>
    /// <param name="ticks">time_point ticks to convert</param>
    /// <returns></returns>
    public static Date TimePointToDate(long ticks)
    {
        return new Date(ticks);
    }

    /// <summary>
    /// Convert DateTime to `time_point_sec` (seconds since epoch)
    /// </summary>
    /// <param name="value">date to convert</param>
    /// <returns></returns>
    public static long DateToTimePointSec(Date value)
    {
        return (value.getTime()/1000L);
   }

    /// <summary>
    /// Convert `time_point_sec` (seconds since epoch) to DateTime
    /// </summary>
    /// <param name="secs">time_point_sec to convert</param>
    /// <returns></returns>
    public static Date TimePointSecToDate(long secs)
    {
        Date x = new Date(secs * 1000L);
        return x;

    }
    public static long halfSecondsSince2000(Date input) {
        final long epoch = 946684800000L;
        return (input.getTime() - epoch) / 500L;
    }
    /// <summary>
    /// Convert DateTime to `block_timestamp_type` (half-seconds since a different epoch)
    /// </summary>
    /// <param name="value">date to convert</param>
    /// <returns></returns>
    public static long DateToBlockTimestamp(Date value)
    {
        return Math.round((double)(value.getTime()-946684800000L)/500.0)&0xffffffff;
    }

    /// <summary>
    /// Convert `block_timestamp_type` (half-seconds since a different epoch) to DateTime
    /// </summary>
    /// <param name="slot">block_timestamp slot to convert</param>
    /// <returns></returns>
    public static Date BlockTimestampToDate(long slot)
    {
        return new Date(slot * 500 + 946684800000L);
    }

    // start bit converter
    public static long ToUInt64(byte[] buf) {
        return ((buf[0] & 0xFFL) << 56) |
                ((buf[1] & 0xFFL) << 48) |
                ((buf[2] & 0xFFL) << 40) |
                ((buf[3] & 0xFFL) << 32) |
                ((buf[4] & 0xFFL) << 24) |
                ((buf[5] & 0xFFL) << 16) |
                ((buf[6] & 0xFFL) <<  8) |
                ((buf[7] & 0xFFL) <<  0) ;
    }
    // end bit converter
    /// <summary>
    /// Convert Name into unsigned long
    /// </summary>
    /// <param name="name"></param>
    /// <returns>Converted value</returns>
    public static long ConvertNameToLong(String name)
    {
        return ToUInt64(ConvertNameToBytes(name));
    }

    /// <summary>
    /// Convert Name into bytes
    /// </summary>
    /// <param name="name"></param>
    /// <returns>Converted value bytes</returns>
    public static byte[] ConvertNameToBytes(String name)
    {
        byte[] a = new byte[8];
        int bit = 63;
        for (int i = 0; i < name.length(); ++i)
        {
            byte c = SerializationHelper.CharToSymbol(name.charAt(i));
            if (bit < 5)
                c = (byte)(c << 1);
            for (int j = 4; j >= 0; --j)
            {
                if (bit >= 0)
                {
                    a[(int)Math.floor((double)(bit / 8))] |= (byte)(((c >> j) & 1) << (bit % 8));
                    --bit;
                }
            }
        }
        return a;
    }


    public static String ReverseHex(String h)
    {
        return h.substring(6,8)+h.substring(4,6)+h.substring(2,4)+h.substring(0,2);
    }
    public static ZSWAPIV1.AbiType firstOrDefaultNewTypeName(List<ZSWAPIV1.AbiType> list, String newTypeName){
        if( list == null ){
            return null;
        }
        for(ZSWAPIV1.AbiType x : list) {
            if(x.new_type_name.equals(newTypeName)){
                return x;
            }
        }
        return null;
    }
    public static ZSWAPIV1.AbiStruct firstOrDefaultNameAbiStruct(List<ZSWAPIV1.AbiStruct> list, String name){
        if( list == null ){
            return null;
        }
        for(ZSWAPIV1.AbiStruct x : list) {
            if(x.name.equals(name)){
                return x;
            }
        }
        return null;
    }
    public static ZSWAPIV1.AbiAction firstOrDefaultNameAbiAction(List<ZSWAPIV1.AbiAction> list, String name){
        if( list == null ){
            return null;
        }
        for(ZSWAPIV1.AbiAction x : list) {
            if(x.name.equals(name)){
                return x;
            }
        }
        return null;
    }
    public static ZSWAPIV1.Variant firstOrDefaultNameVariant(List<ZSWAPIV1.Variant> list, String name){
        if( list == null ){
            return null;
        }
        for(ZSWAPIV1.Variant x : list) {
            if(x.name.equals(name)){
                return x;
            }
        }
        return null;
    }
}

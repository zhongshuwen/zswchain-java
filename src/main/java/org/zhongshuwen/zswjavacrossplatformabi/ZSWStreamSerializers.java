package org.zhongshuwen.zswjavacrossplatformabi;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.firebirdsql.decimal.Decimal128;
import org.zhongshuwen.zswjava.abitypes.Symbol;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.abitypes.ZSWExtendedAsset;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeError;
import org.zhongshuwen.zswjava.interfaces.IRPCProvider;
import org.zhongshuwen.zswjava.models.ZSWName;

import java.math.BigInteger;
import java.text.DateFormat;
import java.time.Instant;
import java.util.*;

public class ZSWStreamSerializers {
    public static Date parseDateObject(Object date) throws Exception {
        if(date instanceof Date){
            return (Date)date;
        }else if(date instanceof Instant){
            return Date.from((Instant) date);
        }else if(date instanceof String){
            String d = (String)date;
            if(d.charAt(d.length()-1)!='Z'){
                return Date.from(Instant.parse((String)(date+"Z")));


            }else {
                return Date.from(Instant.parse((String) date));
            }
        }else if(date instanceof Long){
            return new Date((Long)date);
        }else{
            throw new Exception("Cannot parse date format!");
        }
    }
    public interface WriteDelegate {
        void Write(ZSWWriteStream writeStream, Object data) throws Exception;
    }
    public interface ReadDelegate {
        Object Read(ZSWReadStream readStream, boolean isOptional) throws Exception;
    }
    public static long getLongVersion(Object value){
        if(value instanceof Double){
            return ((Double) value).longValue();
        }else if(value instanceof String) {
            return Long.parseLong((String)value);
        }else if(value instanceof Long) {
            return (Long) value;
        }else if(value instanceof Integer) {
            return ((Integer) value).longValue();
        }else{
            return Long.parseLong(value+"");
        }
    }
    public static long getIntVersion(Object value){
        if(value instanceof Double){
            return ((Double) value).intValue();
        }else if(value instanceof String) {
            return Integer.parseInt((String)value);
        }else if(value instanceof Long) {
            return (int)(((long)value)&0xffffffffL);
        }else if(value instanceof Integer) {
            return (int)value;
        }else{
            return Integer.parseInt(value+"");
        }
    }
    public Hashtable<String, WriteDelegate> TypeWriters;
    public Hashtable<String, ReadDelegate> TypeReaders;

    public ZSWStreamSerializers(){
        TypeWriters = new Hashtable<>();
        TypeReaders = new Hashtable<>();

        TypeWriters.put(
                "int8",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteByte((byte)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "uint8",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteByte((byte)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "int16",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteUint16((int)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "uint16",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteUint16((int)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "int32",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteUint32((int)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "uint32",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteUint32(getLongVersion(data));//(int)getIntVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "int64",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteInt64((long)getLongVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "uint64",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteUint64((long)getLongVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "int128",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteInt128(new BigInteger(((String)data)));
                    }
                }
        );


        TypeWriters.put(
                "uint128",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteUInt128(new BigInteger((String)data));
                    }
                }
        );


        TypeWriters.put(
                "varuint32",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteVarUint32(getLongVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "varint32",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteVarInt32((int)getLongVersion(data));
                    }
                }
        );


        TypeWriters.put(
                "float32",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteFloat32((float)data);
                    }
                }
        );


        TypeWriters.put(
                "float64",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteFloat64((double)data);
                    }
                }
        );


        TypeWriters.put(
                "float128",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteFloat128(Decimal128.valueOf((String)data));
                    }
                }
        );


        TypeWriters.put(
                "bytes",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        if(data instanceof String){
                            writeStream.WriteBytes(ByteUtils.fromHexString((String)data));
                        }else {
                            // todo: check for list? (List<int> or List<byte> from json serializer)
                            writeStream.WriteBytes((byte[]) data);
                        }
                    }
                }
        );


        TypeWriters.put(
                "bool",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteBool((boolean)data);
                    }
                }
        );


        TypeWriters.put(
                "string",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteString((String)data);
                    }
                }
        );


        TypeWriters.put(
                "name",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        if(data instanceof String){
                            writeStream.WriteName((String)data);
                        }else if(data instanceof ZSWName){
                            writeStream.WriteName(((ZSWName) data).getAccountName());
                        }else{
                            throw new Exception("unknown name type!");
                        }
                    }
                }
        );


        TypeWriters.put(
                "asset",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteAsset((String)data);
                    }
                }
        );


        TypeWriters.put(
                "time_point",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteTimePoint(parseDateObject(data));
                    }
                }
        );


        TypeWriters.put(
                "time_point_sec",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteTimePointSec(parseDateObject(data));
                    }
                }
        );


        TypeWriters.put(
                "block_timestamp_type",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteBlockTimestampType(parseDateObject(data));
                    }
                }
        );


        TypeWriters.put(
                "symbol_code",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) {
                        writeStream.WriteSymbolCode((String)data);
                    }
                }
        );


        TypeWriters.put(
                "symbol",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        if(data instanceof String){
                            writeStream.WriteSymbolString((String)data);
                        }else{
                            writeStream.WriteSymbolString(data.toString());
                        }
                    }
                }
        );


        TypeWriters.put(
                "checksum160",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteChecksum160((String)data);
                    }
                }
        );


        TypeWriters.put(
                "checksum256",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteChecksum256((String)data);
                    }
                }
        );


        TypeWriters.put(
                "checksum512",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteChecksum512((String)data);
                    }
                }
        );


        TypeWriters.put(
                "public_key",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WritePublicKey((String)data);
                    }
                }
        );


        TypeWriters.put(
                "private_key",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WritePrivateKey((String)data);
                    }
                }
        );


        TypeWriters.put(
                "signature",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteSignature((String)data);
                    }
                }
        );


        TypeWriters.put(
                "extended_asset",
                new WriteDelegate() {
                    @Override
                    public void Write(ZSWWriteStream writeStream, Object data) throws Exception {
                        writeStream.WriteExtendedAsset((ZSWExtendedAsset) data);
                    }
                }
        );

        // start type readers

        TypeReaders.put(
                "int8",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadByte();
                    }
                }
        );

        TypeReaders.put(
                "uint8",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return (byte)getIntVersion(readStream.ReadByte());
                    }
                }
        );

        TypeReaders.put(
                "int16",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return getIntVersion(readStream.ReadUint16());
                    }
                }
        );

        TypeReaders.put(
                "uint16",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return getIntVersion(readStream.ReadUint16());
                    }
                }
        );

        TypeReaders.put(
                "int32",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadUint32();
                    }
                }
        );

        TypeReaders.put(
                "uint32",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadUint32();
                    }
                }
        );

        TypeReaders.put(
                "int64",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadInt64();
                    }
                }
        );

        TypeReaders.put(
                "uint64",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadUint64();
                    }
                }
        );

        TypeReaders.put(
                "int128",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadInt128();
                    }
                }
        );

        TypeReaders.put(
                "uint128",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadUInt128();
                    }
                }
        );

        TypeReaders.put(
                "varuint32",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadVarUint32();
                    }
                }
        );

        TypeReaders.put(
                "varint32",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadVarInt32();
                    }
                }
        );

        TypeReaders.put(
                "float32",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadFloat32();
                    }
                }
        );

        TypeReaders.put(
                "float64",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadFloat64();
                    }
                }
        );

        TypeReaders.put(
                "float128",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadFloat128();
                    }
                }
        );

        TypeReaders.put(
                "bytes",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadBytes();
                    }
                }
        );

        TypeReaders.put(
                "bool",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadBool();
                    }
                }
        );

        TypeReaders.put(
                "string",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        Object outStr = readStream.ReadString();
                        if(outStr == null && !isOptional){
                            return "";

                        }else{
                            return outStr;
                        }

                    }
                }
        );

        TypeReaders.put(
                "name",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadName();
                    }
                }
        );

        TypeReaders.put(
                "asset",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadAsset();
                    }
                }
        );

        TypeReaders.put(
                "time_point",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadTimePoint();
                    }
                }
        );

        TypeReaders.put(
                "time_point_sec",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadTimePointSec();
                    }
                }
        );

        TypeReaders.put(
                "block_timestamp_type",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadBlockTimestampType();
                    }
                }
        );

        TypeReaders.put(
                "symbol_code",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadSymbolCode();
                    }
                }
        );

        TypeReaders.put(
                "symbol",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadSymbolString();
                    }
                }
        );

        TypeReaders.put(
                "checksum160",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadChecksum160();
                    }
                }
        );

        TypeReaders.put(
                "checksum256",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadChecksum256();
                    }
                }
        );

        TypeReaders.put(
                "checksum512",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadChecksum512();
                    }
                }
        );

        TypeReaders.put(
                "public_key",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) throws DeserializeError {
                        return readStream.ReadPublicKey();
                    }
                }
        );

        TypeReaders.put(
                "private_key",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) throws DeserializeError {
                        return readStream.ReadPrivateKey();
                    }
                }
        );

        TypeReaders.put(
                "signature",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) throws DeserializeError {
                        return readStream.ReadSignature();
                    }
                }
        );

        TypeReaders.put(
                "extended_asset",
                new ReadDelegate() {
                    @Override
                    public Object Read(ZSWReadStream readStream, boolean isOptional) {
                        return readStream.ReadExtendedAsset();
                    }
                }
        );
    }






}

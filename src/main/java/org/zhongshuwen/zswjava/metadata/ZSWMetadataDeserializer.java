package org.zhongshuwen.zswjava.metadata;

import com.google.gson.internal.LinkedTreeMap;
import org.bitcoinj.core.Base58;
import org.bouncycastle.util.encoders.Hex;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsSchemaFieldDefinition;
import org.zhongshuwen.zswjavacrossplatformabi.ZSWReadStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZSWMetadataDeserializer {

    private static final long RESERVED = 4;


/*
    uint64_t zigzagEncode(int64_t value) {
        if (value < 0) {
            return (uint64_t)(-1 * (value + 1)) * 2 + 1;
        } else {
            return (uint64_t) value * 2;
        }
    }

    int64_t zigzagDecode(uint64_t value) {
        if (value&1 == 0) {
            return (int64_t)(value / 2);
        } else {
            return (int64_t)(value / 2) * -1 - 1;
        }
    }

 */
    static long zigzagDecode(long value){
        if ((value&1L) == 0L) {
            return (long)(value / 2L);
        } else {
            return (long)(value / 2L) * -1L - 1L;
        }
    }
    static long zigzagEncode(long value){
        return value;
    }
    public static Object deserializeAttribute(ZSWReadStream rs, String type) throws Exception {
        int typeStrLen = type.length();
        if(typeStrLen == 0){
            return null;
        }

        if(type.charAt(typeStrLen-1) == ']'&&type.charAt(typeStrLen-2) == '['){
            int arrayLength = (int)((long)rs.ReadVarUint32());
            String baseType = type.substring(0, typeStrLen - 2);
            if (baseType == "int8"){
                byte[] list = new byte[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (byte)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "int16" || baseType == "int32" || baseType == "uint8" || baseType == "uint16" || baseType == "fixed8" || baseType == "fixed16" || baseType == "byte"){
                int[] list = new int[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (int)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "int64" || baseType == "uint32" || baseType == "fixed32"){
                long[] list = new long[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (long)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "uint64" || baseType == "fixed64" || baseType == "string" || baseType == "image" || baseType == "ipfs"){
                String[] list = new String[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (String)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "float"){
                float[] list = new float[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (float)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "double"){
                double[] list = new double[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (double)deserializeAttribute(rs, type);
                }
                return list;
            }else if (baseType == "bool"){
                boolean[] list = new boolean[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (boolean)deserializeAttribute(rs, type);
                }
                return list;
            }else{
                Object[] list = new Object[arrayLength];
                for(int i=0;i<arrayLength;i++){
                    list[i] = (Object)deserializeAttribute(rs, type);
                }
                return list;
            }
        }


        if (type == "int8") {
            return (byte) zigzagDecode((long)rs.ReadVarUint32());
        } else if (type == "int16") {
            return (int) zigzagDecode((long)rs.ReadVarUint32());
        } else if (type == "int32") {
            return (int) zigzagDecode((long)rs.ReadVarUint32());
        } else if (type == "int64") {
            return ((long) zigzagDecode((long)rs.ReadVarUint32()))+"";
        } else if (type == "uint8") {
            return (int) ((long)rs.ReadVarUint32());
        } else if (type == "uint16") {
            return (int) (((long)rs.ReadVarUint32())&0xffffffff);
        } else if (type == "uint32") {
            return (long) rs.ReadVarUint32();
        } else if (type == "uint64") {
            return Long.toUnsignedString((long)rs.ReadVarUint32());
        } else if (type == "fixed8") {
            return (int) rs.ReadByte();
        } else if (type == "fixed16") {
            return (int) rs.ReadUint16();
        } else if (type == "fixed32") {
            return (long) rs.ReadUint32();
        } else if (type == "fixed64") {
            return Long.toUnsignedString((long)rs.ReadUint64());//(uint64_t) unsignedFromIntBytes(itr, 8);
        } else if (type == "float") {
            return (float)rs.ReadFloat32LE();
        } else if (type == "double") {
            return (double)rs.ReadFloat64LE();
        } else if (type == "string" || type == "image") {
            return rs.ReadString();
        } else if (type == "ipfs") {
            return Base58.encode(Hex.decode((String)rs.ReadBytes()));
        } else if (type == "bool") {
            return rs.bb.get() != 0;
        }else if(type == "byte"){
            return (int)rs.ReadByte();
        }else{
            throw new Exception("Type not supported "+type);
        }

    }
    public static LinkedTreeMap<String, Object> GenerateLinkedTreeMapFromKVPairs(List<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> kvFields){
        LinkedTreeMap<String, Object> ltm = new LinkedTreeMap<>();
        for(ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>> f : kvFields){
            ltm.put(f.key, f.value.value);
        }
        return ltm;
    }

    public static List<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> GenerateKVPairsFromLinkedTreeMapAndSchema(LinkedTreeMap<String, Object> metaObject, ZSWItemsSchemaFieldDefinition[] fields){
        List<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> kvPairs = new ArrayList<>();
        for(ZSWItemsSchemaFieldDefinition sfd : fields){
            if(metaObject.containsKey(sfd.name)){
                ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>> kvp = new ZSWAPIV1.KVPair<>();
                kvp.key = sfd.name;
                kvp.value = new ZSWAPIV1.KVPair<>();
                kvp.value.key = sfd.type;
                kvp.value.value = metaObject.get(sfd.name);
                kvPairs.add(kvp);
            }
        }
        return kvPairs;
    }

    public static List<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> decodeMetadata(byte[] data, ZSWItemsSchemaFieldDefinition[] fields) throws Exception {
        ArrayList<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> lines = new ArrayList<>();
        ZSWReadStream rs = new ZSWReadStream(data);
        while(rs.bb.hasRemaining()){
            int sz = (int)((long)rs.ReadVarUint32()-RESERVED);
            if(sz>=fields.length||sz<0){
                throw new ArrayIndexOutOfBoundsException("Invalid format field specified in serialized data!");
            }
            ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>> x = new ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>();
            x.key = fields[sz].name;
            x.value = new ZSWAPIV1.KVPair<>();
            x.value.key = fields[sz].type;
            x.value.value = deserializeAttribute(rs, fields[sz].type);
            lines.add(x);
        }
        return lines;

    }

}

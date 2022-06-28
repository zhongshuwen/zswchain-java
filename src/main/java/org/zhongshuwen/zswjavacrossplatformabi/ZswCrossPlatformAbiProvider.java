package org.zhongshuwen.zswjavacrossplatformabi;

import com.google.errorprone.annotations.Var;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import org.bouncycastle.util.encoders.Hex;
import org.zhongshuwen.zswjava.abitypes.PermissionLevel;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializeError;
import org.zhongshuwen.zswjava.interfaces.IRPCProvider;
import org.zhongshuwen.zswjava.models.rpcProvider.Action;
import org.zhongshuwen.zswjava.models.rpcProvider.Authorization;
import org.zhongshuwen.zswjava.models.rpcProvider.Transaction;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZswCrossPlatformAbiProvider {
    public ZSWStreamSerializers zss;

    public static final int DEFAULT_WRITER_BUFFER_SIZE_ACTION = 1024*1024*2;
    private int writerBufferSizeAction = DEFAULT_WRITER_BUFFER_SIZE_ACTION;

    private Gson gson = null;
    class GsonUTCDateAdapter implements JsonSerializer<Date>,JsonDeserializer<Date> {

        private final DateFormat dateFormat;

        public GsonUTCDateAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);      //This is the format I need
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));                               //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
        }

        @Override public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        @Override public synchronized Date deserialize(JsonElement jsonElement,Type type,JsonDeserializationContext jsonDeserializationContext) {
            try {
                return dateFormat.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }

    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private static class ByteArrayToHexTypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Hex.decode(json.getAsString());//Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Hex.toHexString(src));
        }
    }
    public void setUpGSON() {
        this.gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).registerTypeAdapter(byte[].class, new ByteArrayToHexTypeAdapter())//.setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
    }
    private Gson getGson() {
        if(gson == null){
            setUpGSON();
        }
        return gson;
    }
    public ZswCrossPlatformAbiProvider(){
        zss = new ZSWStreamSerializers();
    }
    public void setWriterBufferSizeAction(int size){
        writerBufferSizeAction = size;
    }
    public int getWriterBufferSizeAction(){
        return writerBufferSizeAction;
    }

/*
    /// <summary>
    /// Serialize transaction to packed asynchronously
    /// </summary>
    /// <param name="trx">transaction to pack</param>
    /// <returns></returns>
    public byte[] SerializePackedTransaction(ZSWAPIV1.ApiTransaction trx)
    {
        int actionIndex = 0;
        var abiResponses = await GetTransactionAbis(trx);

        using (MemoryStream ms = new MemoryStream())
        {
            //trx headers
            WriteUint32(ms, SerializationHelper.DateToTimePointSec(trx.expiration));
            WriteUint16(ms, trx.ref_block_num);
            WriteUint32(ms, trx.ref_block_prefix);

            //trx info
            WriteVarUint32(ms, trx.max_net_usage_words);
            WriteByte(ms, trx.max_cpu_usage_ms);
            WriteVarUint32(ms, trx.delay_sec);

            WriteVarUint32(ms, (UInt32)trx.context_free_actions.Count);
            foreach (var action in trx.context_free_actions)
            {
                WriteAction(ms, action, abiResponses[actionIndex++]);
            }

            WriteVarUint32(ms, (UInt32)trx.actions.Count);
            foreach (var action in trx.actions)
            {
                WriteAction(ms, action, abiResponses[actionIndex++]);
            }

            WriteVarUint32(ms, (UInt32)trx.transaction_extensions.Count);
            foreach (var extension in trx.transaction_extensions)
            {
                WriteExtension(ms, extension);
            }

            return ms.ToArray();
        }
    }

 */
    /// <summary>
    /// Serialize action to packed action data
    /// </summary>
    /// <param name="action">action to pack</param>
    /// <param name="abi">abi schema to look action structure</param>
    /// <returns></returns>
public byte[] SerializeActionData(ZSWAPIV1.ApiAction action, ZSWAPIV1.Abi abi) throws SerializeError {
    ZSWAPIV1.AbiAction abiAction = SerializationHelper.firstOrDefaultNameAbiAction(abi.actions,action.name);
    //(abi.actions.FirstOrDefault(aa => aa.name == action.name);

    if (abiAction == null)
        throw new SerializeError(String.format("action name %s not found on abi.", action.name));

    ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, abiAction.type);


    if (abiStruct == null)
        throw new SerializeError(String.format("struct type %s not found on abi.", abiAction.type));
    ZSWWriteStream ws = new ZSWWriteStream(getWriterBufferSizeAction());
    WriteAbiStruct(ws, action.data, abiStruct, abi);
    int size = ws.bb.position();
    byte[] bytesArray = new byte[size];
    ws.bb.position(0);
    ws.bb.get(bytesArray, 0, bytesArray.length);
    return bytesArray;
}
    public LinkedTreeMap<String, Object> DeserializeActionData(byte[] data, String actionName, ZSWAPIV1.Abi abi) throws Exception {
        ZSWAPIV1.AbiAction abiAction = SerializationHelper.firstOrDefaultNameAbiAction(abi.actions,actionName);
        if (abiAction == null)
            throw new DeserializeError(String.format("action name %s not found on abi.", actionName));

        ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, abiAction.type);


        if (abiStruct == null)
            throw new SerializeError(String.format("struct type %s not found on abi.", abiAction.type));
        ZSWReadStream rs = new ZSWReadStream(data);

        return ReadAbiStruct(rs, abiStruct, abi);
    }
public byte[] SerializeStruct(Object base, String structTypeName, ZSWAPIV1.Abi abi) throws SerializeError {
    ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, structTypeName);
    if (abiStruct == null)
        throw new SerializeError(String.format("struct type %s not found on abi.", structTypeName));
    ZSWWriteStream ws = new ZSWWriteStream(getWriterBufferSizeAction()*2);
    WriteAbiStruct(ws, base, abiStruct, abi);
    int size = ws.bb.position();
    byte[] bytesArray = new byte[size];
    ws.bb.position(0);
    ws.bb.get(bytesArray, 0, bytesArray.length);
    return bytesArray;
}
    public byte[] SerializeAbiData(Object base, String typeName, ZSWAPIV1.Abi abi) throws SerializeError {
        ZSWWriteStream ws = new ZSWWriteStream(getWriterBufferSizeAction()*2);
        WriteAbiType(ws, base, typeName, abi, true);
        int size = ws.bb.position();
        byte[] bytesArray = new byte[size];
        ws.bb.position(0);
        ws.bb.get(bytesArray, 0, bytesArray.length);
        return bytesArray;
    }

 /*
    public byte[] SerializeGeneric(Transaction action, ZSWAPIV1.Abi abi) throws SerializeError {
       ZSWAPIV1.AbiAction abiAction = SerializationHelper.firstOrDefaultNameAbiAction(abi.actions,action.name);
        //(abi.actions.FirstOrDefault(aa => aa.name == action.name);

        if (abiAction == null)
            throw new SerializeError(String.format("action name %s not found on abi.", action.name));

        ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, abiAction.type);


        if (abiStruct == null)
            throw new SerializeError(String.format("struct type %s not found on abi.", abiAction.type));
        ZSWWriteStream ws = new ZSWWriteStream(getWriterBufferSizeAction());
        WriteAbiStruct(ws, action.data, abiStruct, abi);
        int size = ws.bb.position();
        byte[] bytesArray = new byte[size];
        ws.bb.position(0);
        ws.bb.get(bytesArray, 0, bytesArray.length);
        return bytesArray;
    }

  */
    private void WriteAction(ZSWWriteStream ws, ZSWAPIV1.ApiAction action, ZSWAPIV1.Abi abi) throws SerializeError {
        ws.WriteName(action.account);
        ws.WriteName(action.name);
        ws.WriteVarUint32(action.authorization.size());
        for(PermissionLevel pl : action.authorization){
            ws.WritePermissionLevel(pl);
        }
        ws.WriteBytes(SerializeActionData(action, abi));
    }

    private void WriteAbiType(ZSWWriteStream ws, Object value, String type, ZSWAPIV1.Abi abi, boolean isBinaryExtensionAllowed) throws SerializeError {
        String uwtype = UnwrapTypeDef(abi, type);

        // binary extension type
        if(uwtype.endsWith("$"))
        {
            if (!isBinaryExtensionAllowed) throw new SerializeError("Binary Extension type not allowed.");
            WriteAbiType(ws, value, uwtype.substring(0, uwtype.length() - 1), abi, isBinaryExtensionAllowed);

            return;
        }

        //optional type
        if (uwtype.endsWith("?"))
        {
            if(value != null)
            {
                ws.WriteByte( (byte)1);
                type = uwtype.substring(0, uwtype.length() - 1);
            }
            else
            {
                ws.WriteByte((byte)0);
                return;
            }
        }

        // array type
        if(uwtype.endsWith("[]"))
        {
            Collection items = (Collection) value;
            String arrayType = uwtype.substring(0, uwtype.length() - 2);

            ws.WriteVarUint32(items.size());
            for(Object item : items){
                WriteAbiType(ws, item, arrayType, abi,false);
            }
            return;
        }


        ZSWStreamSerializers.WriteDelegate writer = GetTypeSerializerWriteDelegateAndCache(type, zss.TypeWriters, abi);
        if (writer != null)
        {
            try {
                writer.Write(ws, value);
            }catch (Exception ex){
                throw new SerializeError(ex);
            }
            return;
        }

        ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, uwtype);


                //abi.structs.FirstOrDefault(s => s.name == uwtype);
        if (abiStruct != null)
        {
            WriteAbiStruct(ws, value, abiStruct, abi);
            return;
        }

        ZSWAPIV1.Variant abiVariant = SerializationHelper.firstOrDefaultNameVariant(abi.variants, uwtype);
        //abi.variants.FirstOrDefault(v => v.name == uwtype);
        if (abiVariant != null)
        {
            WriteAbiVariant(ws, value, abiVariant, abi, isBinaryExtensionAllowed);
        }
        else
        {
            throw new SerializeError("Type supported writer not found.");
        }
    }

    private String FindObjectFieldName(String name, Dictionary<String, Object> value)
    {
        if(value.get(name) != null){
            return name;
        }

        name = SerializationHelper.SnakeCaseToPascalCase(name);

        if(value.get(name) != null) {
            return name;
        }

        name = SerializationHelper.PascalCaseToSnakeCase(name);

        if(value.get(name) != null) {
            return name;
        }

        return null;
    }
    private String FindObjectFieldNameLinkedTreeMap(String name, LinkedTreeMap value)
    {
        if(value.get(name) != null){
            return name;
        }

        name = SerializationHelper.SnakeCaseToPascalCase(name);

        if(value.get(name) != null) {
            return name;
        }

        name = SerializationHelper.PascalCaseToSnakeCase(name);

        if(value.get(name) != null) {
            return name;
        }

        return null;
    }
    private void WriteAbiStruct(ZSWWriteStream ws, Object value, ZSWAPIV1.AbiStruct abiStruct, ZSWAPIV1.Abi abi) throws SerializeError {
        if (value == null)
            return;

        if(abiStruct.base != null && !abiStruct.base.trim().isEmpty())
        {
            WriteAbiType(ws, value, abiStruct.base, abi, true);
        }

        if(value instanceof Dictionary)
        {
            boolean skippedBinaryExtension = false;
            Dictionary<String, Object> valueDict = (Dictionary<String, Object>)value;
            for (ZSWAPIV1.AbiField field : abiStruct.fields)
            {
                String fieldName = FindObjectFieldName(field.name, valueDict);

                if (fieldName == null || fieldName.trim().isEmpty())
                {
                    if (field.type.endsWith("$"))
                    {
                        skippedBinaryExtension = true;
                        continue;
                    }

                    throw new SerializeError("Missing " + abiStruct.name + "." + field.name + " (type=" + field.type + ")");
                }
                else if (skippedBinaryExtension)
                {
                    throw new SerializeError("Unexpected " + abiStruct.name + "." + field.name + " (type=" + field.type + ")");
                }

                WriteAbiType(ws, valueDict.get(fieldName), field.type, abi, true);
            }
        } else if(value instanceof LinkedTreeMap) {
            boolean skippedBinaryExtension = false;
            LinkedTreeMap ltm = (LinkedTreeMap)value;

            for (ZSWAPIV1.AbiField field : abiStruct.fields)
            {
                String fieldName = FindObjectFieldNameLinkedTreeMap(field.name, ltm);

                if (fieldName == null || fieldName.trim().isEmpty())
                {
                    if (field.type.endsWith("$"))
                    {
                        skippedBinaryExtension = true;
                        continue;
                    }

                    throw new SerializeError("Missing " + abiStruct.name + "." + field.name + " (type=" + field.type + ")");
                }
                else if (skippedBinaryExtension)
                {
                    throw new SerializeError("Unexpected " + abiStruct.name + "." + field.name + " (type=" + field.type + ")");
                }

                WriteAbiType(ws, ltm.get(fieldName), field.type, abi, true);
            }

        }else {
            for(ZSWAPIV1.AbiField field: abiStruct.fields) {
                try {
                    Field fieldInfo = value.getClass().getField(field.name);
                    if(fieldInfo != null) {
                            WriteAbiType(ws, fieldInfo.get(value), field.type, abi, true);

                    }else {
                        throw new SerializeError("Missing " + abiStruct.name + "." + field.name + " (type=" + field.type + ")");
                    }
                }catch(Exception ex){
                    throw new SerializeError(ex);
                }
            }
        }
    }


    private void WriteAbiVariant(ZSWWriteStream ws, Object value, ZSWAPIV1.Variant abiVariant, ZSWAPIV1.Abi abi, boolean isBinaryExtensionAllowed) throws SerializeError {
        ZSWAPIV1.KVPair<String,Object> variantValue = (ZSWAPIV1.KVPair<String,Object>)value;
        int i = abiVariant.types.indexOf(variantValue.key);
        if (i < 0)
        {
            throw new SerializeError("type " + variantValue.key + " is not valid for variant");
        }
        ws.WriteVarUint32(i);
        WriteAbiType(ws, variantValue.value, variantValue.key, abi, isBinaryExtensionAllowed);
    }

    private String UnwrapTypeDef(ZSWAPIV1.Abi abi, String type)
    {
        ZSWAPIV1.AbiType wtype = SerializationHelper.firstOrDefaultNewTypeName(abi.types, type);

                //abi.types.FirstOrDefault(t => t.new_type_name == type);
        if(wtype != null && wtype.type.equals(type))
        {
            return UnwrapTypeDef(abi, wtype.type);
        }

        return type;
    }

    private ZSWStreamSerializers.WriteDelegate GetTypeSerializerWriteDelegateAndCache(String type, Dictionary<String, ZSWStreamSerializers.WriteDelegate> typeSerializers, ZSWAPIV1.Abi abi)
    {
        ZSWStreamSerializers.WriteDelegate wd = typeSerializers.get(type);
        if(wd != null){
            return wd;
        }

        ZSWAPIV1.AbiType abiTypeDef = SerializationHelper.firstOrDefaultNewTypeName(abi.types, type);
        //abi.types.FirstOrDefault(t => t.new_type_name == type);

        if(abiTypeDef != null)
        {
            ZSWStreamSerializers.WriteDelegate serializer = GetTypeSerializerWriteDelegateAndCache(abiTypeDef.type, typeSerializers, abi);

            if(serializer != null)
            {
                typeSerializers.put(type, serializer);
                return serializer;
            }
        }

        return null;
    }
    private ZSWStreamSerializers.ReadDelegate GetTypeSerializerReadDelegateAndCache(String type, Dictionary<String, ZSWStreamSerializers.ReadDelegate> typeSerializers, ZSWAPIV1.Abi abi)
    {
        ZSWStreamSerializers.ReadDelegate rd = typeSerializers.get(type);
        if(rd != null){
            return rd;
        }

        ZSWAPIV1.AbiType abiTypeDef = SerializationHelper.firstOrDefaultNewTypeName(abi.types, type);
        //abi.types.FirstOrDefault(t => t.new_type_name == type);

        if(abiTypeDef != null)
        {
            ZSWStreamSerializers.ReadDelegate serializer = GetTypeSerializerReadDelegateAndCache(abiTypeDef.type, typeSerializers, abi);

            if(serializer != null)
            {
                typeSerializers.put(type, serializer);
                return serializer;
            }
        }

        return null;
    }

    // start read


    private Object ReadAbiType(ZSWReadStream rs, String type, ZSWAPIV1.Abi abi, boolean isBinaryExtensionAllowed) throws Exception {
        String uwtype = UnwrapTypeDef(abi, type);

        // binary extension type
        if(uwtype.endsWith("$"))
        {
            if (!isBinaryExtensionAllowed) throw new Exception("Binary Extension type not allowed.");
            return ReadAbiType(rs, uwtype.substring(0, uwtype.length() - 1), abi, isBinaryExtensionAllowed);
        }

        //optional type
        if (uwtype.endsWith("?"))
        {
            byte opt = rs.bb.get();

            if (opt == 0)
            {
                return null;
            }
        }

        // array type
        if (uwtype.endsWith("[]"))
        {
            String arrayType = uwtype.substring(0, uwtype.length() - 2);
            int size = (int)(((long)rs.ReadVarUint32())&0xffffffffL);
            ArrayList<Object> items = new ArrayList<Object>();

            for (int i = 0; i < size; i++)
            {
                items.add(ReadAbiType(rs, arrayType, abi, false));
            }

            return items;
        }

        ZSWStreamSerializers.ReadDelegate reader = GetTypeSerializerReadDelegateAndCache(type, zss.TypeReaders, abi);
        if (reader != null)
        {
            return reader.Read(rs, uwtype.endsWith("?"));
        }

        ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, uwtype);

        if (abiStruct != null)
        {
            return ReadAbiStruct(rs, abiStruct, abi);
        }

        ZSWAPIV1.Variant abiVariant = SerializationHelper.firstOrDefaultNameVariant(abi.variants, uwtype);

        //abi.variants.FirstOrDefault(v => v.name == uwtype);
        if(abiVariant != null)
        {
            return ReadAbiVariant(rs, abiVariant, abi, isBinaryExtensionAllowed);
        }
        else
        {
            throw new Exception("Type supported writer not found.");
        }
    }
    public LinkedTreeMap<String, Object> DeserializeStruct(byte[] data, String structTypeName, ZSWAPIV1.Abi abi) throws Exception {
        ZSWAPIV1.AbiStruct abiStruct = SerializationHelper.firstOrDefaultNameAbiStruct(abi.structs, structTypeName);
        if (abiStruct == null)
            throw new DeserializeError(String.format("struct type %s not found on abi.", structTypeName));
        ZSWReadStream rs = new ZSWReadStream(data);

        return ReadAbiStruct(rs, abiStruct, abi);
    }
    public String DeserializeDataToJSON(byte[] data, String typeName, ZSWAPIV1.Abi abi) throws Exception {

        ZSWReadStream rs = new ZSWReadStream(data);
        Object obj = ReadAbiType(rs, typeName, abi, true);
        return getGson().toJson(obj);

    }
    private LinkedTreeMap<String, Object> ReadAbiStruct(ZSWReadStream rs, ZSWAPIV1.AbiStruct abiStruct, ZSWAPIV1.Abi abi) throws Exception {
        LinkedTreeMap<String, Object> ltm;
        if(abiStruct.base != null && !abiStruct.base.trim().isEmpty()){
            ltm =  (LinkedTreeMap<String, Object>)ReadAbiType(rs, abiStruct.base, abi, true);
        }else{
            ltm =  new LinkedTreeMap<String, Object>();
        }
        for(ZSWAPIV1.AbiField field : abiStruct.fields){
            Object abiValue = ReadAbiType(rs, field.type, abi, true);
            if (field.type.endsWith("$") && abiValue == null) break;
            ltm.put(field.name, abiValue);

        }
        return ltm;
        //return ReadAbiStruct<Dictionary<String, Object>>(data, abiStruct, abi);
    }


    private Object ReadAbiVariant(ZSWReadStream rs, ZSWAPIV1.Variant abiVariant, org.zhongshuwen.zswjava.abitypes.ZSWAPIV1.Abi abi, boolean isBinaryExtensionAllowed) throws Exception {

        int i = (int)rs.ReadVarUint32();
        if (i >= abiVariant.types.size())
        {
            throw new Exception("type index " + i + " is not valid for variant");
        }
        String type = abiVariant.types.get(i);
        ZSWAPIV1.KVPair<String,Object> kvp = new ZSWAPIV1.KVPair<String,Object>();
        kvp.key = abiVariant.name;;
        kvp.value = ReadAbiType(rs, type, abi, isBinaryExtensionAllowed);
        return kvp;
    }

}

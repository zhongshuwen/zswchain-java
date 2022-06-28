package org.zhongshuwen.zswjavacrossplatformabi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeAbiError;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeError;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializePackedTransactionError;
import org.zhongshuwen.zswjava.error.serializationProvider.DeserializeTransactionError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializationProviderError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializeAbiError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializeError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializePackedTransactionError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializeTransactionError;
import org.zhongshuwen.zswjava.interfaces.IRPCProvider;
import org.zhongshuwen.zswjava.interfaces.ISerializationProvider;
import org.zhongshuwen.zswjava.models.AbiEosSerializationObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zhongshuwen.zswjavaabieosserializationprovider.AbiEosJson;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class ZswCoreSerializationProvider implements ISerializationProvider {
    private Hashtable<String, Class> actionClassMap;

    private String TAG = "ZswCoreSerializationProvider";
    private ZswCrossPlatformAbiProvider abiNew;

    private Gson gson = null;

    public void setUpGSON() {
        if(gson == null)
            this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
    }
    public Gson getGson(){
        return this.gson;
    }
    /**
     * Create a new ZswCoreSerializationProvider serialization provider instance, initilizing a new context for the C++
     * library to work on automatically.
     *
     * @throws SerializationProviderError - An error is thrown if the context cannot be created.
     */
    public ZswCoreSerializationProvider() throws SerializationProviderError {
        abiNew = new ZswCrossPlatformAbiProvider();
        this.setUpGSON();
        this.actionClassMap = new Hashtable<>();

    }

    public static String getClassRegistrationKey(String contract, String name){
        return contract+"~"+name;
    }
    public void registerActionType(String contract, String name, Class actionType){
        actionClassMap.put(getClassRegistrationKey(contract, name), actionType);



    }
    public Class<Serializable> getActionType(String contract, String name){
        return actionClassMap.get(getClassRegistrationKey(contract, name));
    }

    /**
     * Takes the given string and converts it to a 64 bit value.  This value should always be treated
     * as unsigned.  Java does not provide a native unsigned 64 bit integer so a long
     * is used instead.
     * @param str - String to convert to 64 bit value.
     * @return - 64 bit value.  Returned as long but should be treated as an unsigned value.
     * @throws SerializationProviderError - An error is thrown if the context cannot be created.
     */
    public long stringToName64(@Nullable String str) throws SerializationProviderError {
        return SerializationHelper.ConvertNameToLong(str);
        // old.stringToName64(str);
    }

    /**
     * Take the given 64 bit value and converts it to a String.
     * @param name - 64 bit value to convert.  This value should always be treated as unsigned.
     * @return - String equivalent to the 64 bit input value.
     * @throws SerializationProviderError - - An error is thrown if the context cannot be created.
     */
    @NotNull
    public String name64ToString(long name) throws SerializationProviderError {
            byte[] a = new byte[8];
            for (int i = 7; i >= 0; i--) {
                a[i] = (byte)(name & 0xFF);
                name >>= 8;
            }

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

        //return old.name64ToString(name);
    }

    /**
     * Perform a serialization process to convert a JSON string to a HEX string given the parameters
     * provided in the input serializationObject.  The result will be placed in the hex field of
     * the serializationObject and can be accessed with getHex().
     *
     * @param serializationObject - Input object passing the JSON string to be converted as well
     * as other parameters to control the serialization process.
     * @throws SerializeError - A serialization error is thrown if there are any exceptions during the
     * conversion process.
     */
    public void serialize(@NotNull AbiEosSerializationObject serializationObject)
            throws SerializeError {
        if (serializationObject.getAbi().isEmpty()) {
            throw new SerializeError(String.format("serialize -- No ABI provided for %s %s",
                    serializationObject.getContract() == null ? serializationObject
                            .getContract() : "",
                    serializationObject.getName()));
        }
        if(serializationObject.getType() == null){
            ZSWAPIV1.ApiAction action = new ZSWAPIV1.ApiAction();
            action.account = serializationObject.getContract();
            action.name = serializationObject.getName();
            Class actionType = getActionType(serializationObject.getContract(), serializationObject.getName());
            if(actionType == null){
                actionType = Object.class;
                //throw new SerializeError("Unregistered action type "+serializationObject.getContract()+":"+serializationObject.getName());
            }
            ZSWAPIV1.Abi abi = this.gson.fromJson(serializationObject.getAbi(), ZSWAPIV1.Abi.class);
            Object actionObject = this.gson.fromJson(serializationObject.getJson(),actionType);
            action.data = actionObject;

            String hex = ByteUtils.toHexString(abiNew.SerializeActionData(action, abi));

            serializationObject.setHex(hex.toUpperCase());




        }else {
            String type = serializationObject.getType();
            ZSWAPIV1.Abi abi = this.gson.fromJson(serializationObject.getAbi(), ZSWAPIV1.Abi.class);
            Object base = this.gson.fromJson(serializationObject.getJson(), Object.class);
            String hex = ByteUtils.toHexString(abiNew.SerializeAbiData(base, type, abi));
            serializationObject.setHex(hex.toUpperCase());
            /*try {
                LinkedTreeMap ltm = abiNew.DeserializeStruct(ByteUtils.fromHexString(hex), type, abi);
                System.out.println("json: "+this.gson.toJson(ltm));

            }catch(Exception ex){
                throw new SerializeError(ex.toString());
            }*/
        }

        //System.out.println("OLD_HEX: "+oldHex.toUpperCase());
        //System.out.println("NEW_HEX: "+serializationObject.getHex().toUpperCase());

    }

    /**
     * Convenience method to transform a transaction JSON string to a hex string.
     *
     * @param json - JSON string representing the transaction to serialize.
     * @return - Serialized hex string representing the transaction JSON.
     * @throws SerializeTransactionError - A serialization error is thrown if there are any exceptions during the
     *      * conversion process.
     */
    @NotNull
    public String serializeTransaction(String json) throws SerializeTransactionError {
        try {
            String abi = getAbiJsonString("transaction.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "transaction", abi);
            serializationObject.setJson(json);
            serialize(serializationObject);
            return serializationObject.getHex();
        } catch (SerializationProviderError serializationProviderError) {
            throw new SerializeTransactionError(serializationProviderError);
        }
    }

    /**
     * Convenience method to transform an ABI JSON string to a hex string.
     *
     * @param json - JSON string representing the ABI to serialize.
     * @return - Serialized hex string representing the ABI JSON.
     * @throws SerializeAbiError - A serialization error is thrown if there are any exceptions during the
     * conversion process.
     */
    @NotNull
    public String serializeAbi(String json) throws SerializeAbiError {
        try {
            String abi = getAbiJsonString("abi.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "abi_def", abi);
            serializationObject.setJson(json);
            serialize(serializationObject);
            return serializationObject.getHex();
        } catch (SerializationProviderError serializationProviderError) {
            throw new SerializeAbiError(serializationProviderError);
        }
        /*
        try {
            refreshContext();
            boolean jsonToBinResult = abiJsonToBin(context, json);
            if(!jsonToBinResult) {
                String err = error();
                String errMsg = String
                        .format("Unable to pack abi json to bin. %s", err == null ? "" : err);
                throw new SerializeError(errMsg);
            }
            String hex = getBinHex(context);
            if (hex == null) {
                throw new SerializeError("Unable to convert binary to hex.");
            }
            return hex;
        } catch (SerializationProviderError serializationProviderError) {
            throw new SerializeAbiError(serializationProviderError);
        }

         */
    }

    /**
     * Convenience method to transform a serialized transaction (v0) JSON string to a hex string.
     *
     * @param json - JSON string representing the serialized transaction (v0) to serialize.
     * @return - Serialized hex string representing the transaction JSON.
     * @throws SerializePackedTransactionError - A serialization error is thrown if there are any exceptions during the
     *      * conversion process.
     */
    @NotNull
    public String serializePackedTransaction(String json) throws SerializePackedTransactionError {
        try {
            String abi = getAbiJsonString("packed.transaction.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "packed_transaction_v0", abi);
            serializationObject.setJson(json);
            serialize(serializationObject);
            return serializationObject.getHex();
        } catch (SerializationProviderError serializationProviderError) {
            throw new SerializePackedTransactionError(serializationProviderError);
        }
    }

    /**
     * Perform a deserialization process to convert a hex string to a JSON string given the parameters
     * provided in the input deserilizationObject.  The result will be placed in the json field of
     * the deserilizationObject and can be accessed with getJson().
     *
     * @param deserilizationObject - Input object passing the hex string to be converted as well
     * as other parameters to control the deserialization process.
     * @throws DeserializeError - A deserialization error is thrown if there are any exceptions during the
     * conversion process.
     */
    public void deserialize(@NotNull AbiEosSerializationObject deserilizationObject) throws DeserializeError {
        try {
            if (deserilizationObject.getType() == null) {
                ZSWAPIV1.Abi abi = this.gson.fromJson(deserilizationObject.getAbi(), ZSWAPIV1.Abi.class);
                LinkedTreeMap ltm = abiNew.DeserializeActionData(ByteUtils.fromHexString(deserilizationObject.getHex()), deserilizationObject.getName(), abi);
                deserilizationObject.setJson(this.gson.toJson(ltm));
            } else {
                ZSWAPIV1.Abi abi = this.gson.fromJson(deserilizationObject.getAbi(), ZSWAPIV1.Abi.class);
/*
                LinkedTreeMap ltm = abiNew.DeserializeStruct(ByteUtils.fromHexString(deserilizationObject.getHex()), deserilizationObject.getType(), abi);
                deserilizationObject.setJson(this.gson.toJson(ltm));
                */
                deserilizationObject.setJson(abiNew.DeserializeDataToJSON(ByteUtils.fromHexString(deserilizationObject.getHex()), deserilizationObject.getType(), abi));

            }
        }catch(Exception ex){
            throw new DeserializeError(ex.getMessage());
        }
        //old.deserialize(deserilizationObject);
        /*

        try {
            // refreshContext() will throw an error if it can't create the context so we don't need
            // to check it explicitly before this.
            refreshContext();

            if (deserilizationObject.getHex().isEmpty()) {
                throw new DeserializeError("No content to serialize.");
            }

            long contract64 = stringToName64(deserilizationObject.getContract());

            if (deserilizationObject.getAbi().isEmpty()) {
                throw new DeserializeError(String.format("deserialize -- No ABI provided for %s %s",
                        deserilizationObject.getContract() == null ? deserilizationObject
                                .getContract() : "",
                        deserilizationObject.getName()));
            }

            boolean result = setAbi(context, contract64, deserilizationObject.getAbi());
            if (!result) {
                String err = error();
                String errMsg = String
                        .format("deserialize == Unable to set ABI. %s", err == null ? "" : err);
                throw new DeserializeError(errMsg);
            }

            String typeStr = deserilizationObject.getType() == null ?
                    getType(deserilizationObject.getName(), contract64)
                    : deserilizationObject.getType();
            if (typeStr == null) {
                String err = error();
                String errMsg = String.format("Unable to find type for action %s. %s",
                        deserilizationObject.getName(), err == null ? "" : err);
                throw new DeserializeError(errMsg);
            }

            String jsonStr = hexToJson(context, contract64, typeStr, deserilizationObject.getHex());
            if (jsonStr == null) {
                String err = error();
                String errMsg = String
                        .format("Unable to unpack hex to json. %s", err == null ? "" : err);
                throw new DeserializeError(errMsg);
            }

            deserilizationObject.setJson(jsonStr);
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializeError(serializationProviderError);
        }

         */
    }

    /**
     * Convenience method to transform a transaction hex string to a JSON string.
     *
     * @param hex - Hex string representing the transaction to deserialize.
     * @return - Deserialized JSON string representing the transaction hex.
     * @throws DeserializeTransactionError - A deserialization error is thrown if there are any exceptions during the
     *      * conversion process.
     */
    @NotNull
    public String deserializeTransaction(String hex) throws DeserializeTransactionError {
        try {
            String abi = getAbiJsonString("transaction.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "transaction", abi);
            serializationObject.setHex(hex);
            deserialize(serializationObject);
            return serializationObject.getJson();
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializeTransactionError(serializationProviderError);
        }
        //return old.deserializeTransaction(hex);
        /*
        try {
            String abi = getAbiJsonString("transaction.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "transaction", abi);
            serializationObject.setHex(hex);
            deserialize(serializationObject);
            return serializationObject.getJson();
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializeTransactionError(serializationProviderError);
        }

         */
    }

    /**
     * Convenience method to transform an ABI hex string to a JSON string.
     *
     * @param hex - Hex string representing the ABI to deserialize.
     * @return - Deserialized JSON string representing the ABI hex.
     * @throws DeserializeAbiError - A deserialization error is thrown if there are any exceptions during the
     * conversion process.
     */
    @NotNull
    public String deserializeAbi(String hex) throws DeserializeAbiError {
        try {
            String abi = getAbiJsonString("abi.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "abi_def", abi);
            serializationObject.setHex(hex);
            deserialize(serializationObject);
            return serializationObject.getJson();
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializeAbiError(serializationProviderError);
        }
        //return old.deserializeAbi(hex);
        /*
        try {
            byte[] hexData = Hex.decode(hex);
            String jsonStr = abiBinToJson(context, hexData);
            if(jsonStr == null) {
                String err = error();
                String errMsg = String
                        .format("Unable to unpack abi hex to json. %s", err == null ? "" : err);
                throw new DeserializeError(errMsg);
            }
            return jsonStr;
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializeAbiError(serializationProviderError);
        } catch (DecoderException decoderError) {
            throw new DeserializeAbiError(decoderError);
        }

         */
    }

    /**
     * Convenience method to transform a packed transaction (v0) hex string to a JSON string.
     *
     * @param hex - Hex string representing the packed transaction (v0) to deserialize.
     * @return - Deserialized JSON string representing the transaction hex.
     * @throws DeserializePackedTransactionError - A deserialization error is thrown if there are any exceptions during the
     *      * conversion process.
     */
    @NotNull
    public String deserializePackedTransaction(String hex) throws DeserializePackedTransactionError {
        //return old.deserializePackedTransaction(hex);

        try {
            //refreshContext();
            String abi = getAbiJsonString("packed.transaction.abi.json");
            AbiEosSerializationObject serializationObject = new AbiEosSerializationObject(null,
                    "", "packed_transaction_v0", abi);
            serializationObject.setHex(hex);
            deserialize(serializationObject);
            return serializationObject.getJson();
        } catch (SerializationProviderError serializationProviderError) {
            throw new DeserializePackedTransactionError(serializationProviderError);
        }


    }

    /**
     * Return bundled ABI JSON for transaction or ABI serialization/deserialization conversions.
     *
     * @param abi - Name of the ABI JSON template to return.
     * @return - JSON template string for the specified ABI.
     * @throws SerializationProviderError - if the specified ABI JSON template cannot be found and returned.
     */
    @NotNull
    private String getAbiJsonString(@NotNull String abi)  throws SerializationProviderError {

        String abiString;

        Map<String, String> jsonMap = AbiEosJson.abiEosJsonMap;
        if (jsonMap.containsKey(abi)) {
            abiString = jsonMap.get(abi);
        } else {
            System.out.println(TAG + " - Error, no json in map for: " + abi);
            abiString = "";
        }

        if (abiString == null || abiString.isEmpty()) {
            throw new SerializationProviderError(String.format("Serialization Provider -- No ABI found for %s",
                    abi));
        }

        return abiString;

    }




}

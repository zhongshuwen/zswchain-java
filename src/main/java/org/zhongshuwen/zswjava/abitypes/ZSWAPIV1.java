package org.zhongshuwen.zswjava.abitypes;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ZSWAPIV1 {
    public static Gson GetGSON(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
        /*
        JsonSerializer<AbiStruct> serializer =
                new JsonSerializer<AbiStruct>() {
                    @Override
                    public JsonElement serialize(AbiStruct src, Type typeOfSrc, JsonSerializationContext context) {
                        if(src.base == null){
                            src.base = "";
                        }
                        JsonArray jsonMerchant = new JsonArray();

                        for (AbiStruct merchant : src) {
                            jsonMerchant.add("" + merchant.getId());
                        }

                        return jsonMerchant;
                    }
                };

         */
        return gson;
    }

    public class ApiExtension
    {
        //uint16
        public int type;

        public byte[] data;
    }
    public class ApiTransaction
    {

        public Date expiration;
        //uint16
        public int ref_block_num;

        // uint32
        public long ref_block_prefix;
        // uint32
        public long max_net_usage_words;

        public byte max_cpu_usage_ms;

        // uint32
        public long delay_sec;

        public List<ApiAction> context_free_actions = new ArrayList<>();

        public List<ApiAction> actions = new ArrayList<>();

        public List<ApiExtension> transaction_extensions = new ArrayList<>();
    }
    public static class ApiAction implements Serializable
    {

        @SerializedName("account")
        //@NotNull
        public String account;

        @SerializedName("name")
        //@NotNull
        public String name;

        @SerializedName("authorization")
        //@NotNull
        public List<PermissionLevel> authorization;

        @SerializedName("data")
        public Object data;

        @SerializedName("hex_data")
        public String hex_data;
    }
    public static class KVPair<K,V> implements Serializable
    {

        @SerializedName("key")
        //@NotNull
        public K key;

        @SerializedName("value")
        public V value;
    }
    public class AbiType implements Serializable
    {

        @SerializedName("new_type_name")
        //@NotNull
        public String new_type_name;

        @SerializedName("type")
        //@NotNull
        public String type;
    }
    public class AbiField implements Serializable
    {

        @SerializedName("name")
        //@NotNull
        public String name; // todo: add name type for serializing to binary?

        @SerializedName("type")
        //@NotNull
        public String type;
    }
    public class AbiStruct implements Serializable
    {

        @SerializedName("name")
        @NotNull
        public String name;

        @SerializedName("base")
        @NotNull
        public String base = "";

        @SerializedName("fields")

        //@NotNull
        public List<AbiField> fields;
        public AbiStruct(){
        }
    }
    
    public class AbiAction  implements Serializable
    {
		//[AbiFieldType("name")]
        @SerializedName("name")
        //@NotNull
        public String name;

        @SerializedName("type")
        //@NotNull
        public String type;

        @SerializedName("ricardian_contract")
        //@NotNull
        public String ricardian_contract;
    }
    public class AbiTable implements Serializable
    {
		//[AbiFieldType("name")]

        @SerializedName("name")
        //@NotNull
        public String name;

        @SerializedName("index_type")
        //@NotNull
        public String index_type;

        @SerializedName("key_names")
        //@NotNull
        public List<String> key_names;

        @SerializedName("key_types")
        //@NotNull
        public List<String> key_types;

        @SerializedName("type")
        //@NotNull
        public String type;
    }

    public class Variant implements Serializable
    {

        @SerializedName("name")
        //@NotNull
        public String name;

        @SerializedName("types")
        //@NotNull
        public List<String> types;
    }
    public class Abi implements Serializable
    {

        @SerializedName("version")
        @NotNull
        public String version;

        @SerializedName("types")
        @NotNull
        public List<AbiType> types;

        @SerializedName("structs")
        @NotNull
        public List<AbiStruct> structs;

        @SerializedName("actions")
        @NotNull
        public List<AbiAction> actions;

        @SerializedName("tables")
        @NotNull
        public List<AbiTable> tables;

        @SerializedName("ricardian_clauses")
        @NotNull
        public List<AbiRicardianClause> ricardian_clauses;

        @SerializedName("error_messages")
        @NotNull
        public List<String> error_messages;

        @SerializedName("abi_extensions")
        @NotNull
        public List<ZSWV1Extension> abi_extensions;

        @SerializedName("variants")
        @NotNull
        public List<Variant> variants;

        @SerializedName("action_results")
        @NotNull
        public List<String> action_results;



        @SerializedName("kv_tables")
        @NotNull
        public Hashtable<String, String> kv_tables;



    }
    public class AbiRicardianClause implements Serializable
    {

        @SerializedName("id")
        //@NotNull
        public String id;

        @SerializedName("body")
        //@NotNull
        public String body;
    }
}

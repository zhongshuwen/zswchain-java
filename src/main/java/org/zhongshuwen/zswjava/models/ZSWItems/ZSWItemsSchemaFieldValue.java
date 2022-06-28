package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;

import java.io.Serializable;
public class ZSWItemsSchemaFieldValue implements  Serializable{

    @SerializedName("key")
    @NotNull
    public String key;


    @SerializedName("value")
    public ZSWAPIV1.KVPair<String,Object> value;

    //        { "key": "name", "value": ["string", "My first item"] },

    public ZSWItemsSchemaFieldValue(@NotNull String key, @NotNull String type, Serializable value) {

        this.key = key;
        this.value = new ZSWAPIV1.KVPair<String,Object>();
        this.value.key = type;
        this.value.value = value;

    }
}

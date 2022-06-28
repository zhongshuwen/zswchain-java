package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
public class ZSWItemsSchemaFieldValue implements  Serializable{

    @SerializedName("name")
    @NotNull
    public String name;

    @SerializedName("type")
    @NotNull
    public String type;

    @SerializedName("value")
    public Serializable value;

    public ZSWItemsSchemaFieldValue(@NotNull String name, @NotNull String type, Serializable value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}

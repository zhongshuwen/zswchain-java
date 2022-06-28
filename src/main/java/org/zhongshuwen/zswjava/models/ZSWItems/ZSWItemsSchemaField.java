package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
public class ZSWItemsSchemaField implements  Serializable{

    @SerializedName("name")
    @NotNull
    public String name;

    @SerializedName("type")
    @NotNull
    public String type;

    public ZSWItemsSchemaField(@NotNull String name, @NotNull String type) {
        this.name = name;
        this.type = type;
    }
}

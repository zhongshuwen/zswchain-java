package org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTables;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsSchemaFieldDefinition;

import java.io.Serializable;

public class ZSWSchemasTableRow implements Serializable {

    @SerializedName("schema_name")
    @NotNull
    public String schema_name;

    @SerializedName("format")
    @NotNull
    public ZSWItemsSchemaFieldDefinition[] fields;
}

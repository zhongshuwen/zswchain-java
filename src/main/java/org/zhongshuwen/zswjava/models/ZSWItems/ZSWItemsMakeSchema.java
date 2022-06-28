package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ZSWItemsMakeSchema implements Serializable {
/*

  {
    "account": "zsw.items",
    "name": "mkschema",
    "data": {
      "authorizer": "zsw.admin",
      "creator": "kxjdzzzz111a",
      "schema_name": "schematest13",
      "schema_format": [
        { "name": "name", "type": "string" },
        { "name": "艺术家", "type": "string" }
      ]
    }
  },
 */

    @SerializedName("authorizer")
    @NotNull
    public String authorizer;

    @SerializedName("creator")
    @NotNull
    public String creator;

    @SerializedName("schema_name")
    @NotNull
    public String schema_name;

    @SerializedName("schema_format")
    @NotNull
    public ZSWItemsSchemaFieldDefinition[] fields;

    public ZSWItemsMakeSchema(@NotNull String authorizer, @NotNull String creator, @NotNull String schema_name, @NotNull ZSWItemsSchemaFieldDefinition[] fields) {
        this.authorizer = authorizer;
        this.creator = creator;
        this.schema_name = schema_name;
        this.fields = fields;
    }
}

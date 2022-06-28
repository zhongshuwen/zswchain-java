package org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTables;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;

import java.io.Serializable;
/*
[
            {
               "name": "zsw_id",
               "type": "uint128"
            },
            {
               "name": "collection_id",
               "type": "uint64"
            },
            {
               "name": "item_type",
               "type": "uint32"
            },
            {
               "name": "schema_name",
               "type": "name"
            },
            {
               "name": "serialized_immutable_metadata",
               "type": "uint8[]"
            },
            {
               "name": "item_external_metadata_url_template",
               "type": "string"
            }
         ]
 */
public class ZSWItemTemplatesTableRow implements Serializable {


    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;

    @SerializedName("collection_id")
    @NotNull
    public String collection_id;

    @SerializedName("item_type")
    @NotNull
    public int item_type;




    @SerializedName("schema_name")
    @NotNull
    public String schema_name;

    @SerializedName("serialized_immutable_metadata")
    @NotNull
    public int[] serialized_immutable_metadata;


    @SerializedName("item_external_metadata_url_template")
    @NotNull
    public String item_external_metadata_url_template;

}

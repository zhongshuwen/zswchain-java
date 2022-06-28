package org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTables;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/*


            {
               "name": "zsw_id",
               "type": "uint128"
            },
            {
               "name": "item_config",
               "type": "uint32"
            },
            {
               "name": "item_template_id",
               "type": "uint64"
            },
            {
               "name": "collection_id",
               "type": "uint64"
            },
            {
               "name": "issued_supply",
               "type": "uint64"
            },
            {
               "name": "max_supply",
               "type": "uint64"
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
               "name": "serialized_mutable_metadata",
               "type": "uint8[]"
            }
 */
public class ZSWItemsTableRow implements Serializable {

    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;


    @SerializedName("item_config")
    @NotNull
    public int item_config;


    @SerializedName("item_template_id")
    @NotNull
    public String item_template_id;
    @SerializedName("collection_id")
    @NotNull
    public String collection_id;

    @SerializedName("issued_supply")
    @NotNull
    public String issued_supply;

    @SerializedName("max_supply")
    @NotNull
    public long max_supply;


    @SerializedName("schema_name")
    @NotNull
    public String schema_name;


    @SerializedName("serialized_immutable_metadata")
    @NotNull
    public int[] serialized_immutable_metadata;

    @SerializedName("serialized_mutable_metadata")
    @NotNull
    public int[] serialized_mutable_metadata;
}

package org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTables;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;

import java.io.Serializable;
/*

            {
               "name": "zsw_id",
               "type": "uint128"
            },
            {
               "name": "collection_type",
               "type": "uint32"
            },
            {
               "name": "creator",
               "type": "name"
            },
            {
               "name": "issuing_platform",
               "type": "name"
            },
            {
               "name": "item_config",
               "type": "uint32"
            },
            {
               "name": "secondary_market_fee",
               "type": "uint16"
            },
            {
               "name": "primary_market_fee",
               "type": "uint16"
            },
            {
               "name": "royalty_fee_collector",
               "type": "name"
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
               "name": "items_count",
               "type": "uint64"
            },
            {
               "name": "max_items",
               "type": "uint64"
            },
            {
               "name": "max_supply_per_item",
               "type": "uint64"
            },
            {
               "name": "schema_name",
               "type": "name"
            },
            {
               "name": "authorized_minters",
               "type": "name[]"
            },
            {
               "name": "notify_accounts",
               "type": "name[]"
            },
            {
               "name": "authorized_mutable_data_editors",
               "type": "name[]"
            },
            {
               "name": "serialized_metadata",
               "type": "uint8[]"
            },
            {
               "name": "external_metadata_url",
               "type": "string"
            }
 */
public class ZSWCollectionsTableRow implements Serializable {

    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;

    @SerializedName("collection_type")
    @NotNull
    public int collection_type;


    @SerializedName("creator")
    @NotNull
    public String creator;


    @SerializedName("issuing_platform")
    @NotNull
    public String issuing_platform;

    @SerializedName("item_config")
    @NotNull
    public int item_config;


    @SerializedName("secondary_market_fee")
    @NotNull
    public int secondary_market_fee;
    @SerializedName("primary_market_fee")
    @NotNull
    public int primary_market_fee;


    @SerializedName("royalty_fee_collector")
    @NotNull
    public String royalty_fee_collector;

    @SerializedName("issued_supply")
    @NotNull
    public long issued_supply;
    @SerializedName("items_count")
    @NotNull
    public long items_count;
    @SerializedName("max_items")
    @NotNull
    public long max_items;
    @SerializedName("max_supply")
    @NotNull
    public long max_supply;

    @SerializedName("max_supply_per_item")
    @NotNull
    public long max_supply_per_item;

    @SerializedName("schema_name")
    @NotNull
    public String schema_name;

    @SerializedName("authorized_minters")
    @NotNull
    public String[] authorized_minters;

    @SerializedName("notify_accounts")
    @NotNull
    public String[] notify_accounts;

    @SerializedName("authorized_mutable_data_editors")
    @NotNull
    public String[] authorized_mutable_data_editors;

    @SerializedName("serialized_metadata")
    @NotNull
    public int[] serialized_metadata;


    @SerializedName("external_metadata_url")
    @NotNull
    public String external_metadata_url;
}

package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
/*


      "authorizer": "zsw.admin",
      "zsw_id": "2133125",
      "collection_id": "2133125",
      "collection_type": 0,
      "creator": "kxjdzzzz111a",
      "issuing_platform": "kxjdzzzz111a",
      "item_config": 11,
      "secondary_market_fee": 500,
      "primary_market_fee": 500,
      "royalty_fee_collector": "kxjdzzzz111a",
      "max_items": "4294967295",
      "max_supply": "18446744073709551615",
      "max_supply_per_item": 0,
      "schema_name": "schematest13",
      "authorized_minters": ["kxjdzzzz111a"],
      "notify_accounts": [],
      "authorized_mutable_data_editors": [],
      "metadata": [
        { "key": "name", "value": ["string", "这个是一个collection"] },
        { "key": "艺术家", "value": ["string", "卡特"] }

      ],
      "external_metadata_url": "https://example.com/collection.json"
 */
public class ZSWItemsMakeCollection {


    @SerializedName("authorizer")
    @NotNull
    public String authorizer;

    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;

    @SerializedName("collection_id")
    @NotNull
    public String collection_id;

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
    public String item_config;


    @SerializedName("secondary_market_fee")
    @NotNull
    public int secondary_market_fee;
    @SerializedName("primary_market_fee")
    @NotNull
    public int primary_market_fee;


    @SerializedName("royalty_fee_collector")
    @NotNull
    public String royalty_fee_collector;

    @SerializedName("max_items")
    @NotNull
    public long max_items;

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

    @SerializedName("metadata")
    @NotNull
    public ZSWItemsSchemaFieldValue[] metadata;


    @SerializedName("external_metadata_url")
    @NotNull
    public String external_metadata_url;


}

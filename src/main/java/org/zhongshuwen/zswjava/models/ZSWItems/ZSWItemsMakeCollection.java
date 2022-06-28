package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;

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
public class ZSWItemsMakeCollection implements  Serializable{


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

    @SerializedName("metadata")
    @NotNull
    public ZSWAPIV1.KVPair[] metadata;


    @SerializedName("external_metadata_url")
    @NotNull
    public String external_metadata_url;

    public ZSWItemsMakeCollection(@NotNull String authorizer, @NotNull String zsw_id, @NotNull String collection_id, @NotNull int collection_type, @NotNull String creator, @NotNull String issuing_platform, @NotNull int item_config, @NotNull int secondary_market_fee, @NotNull int primary_market_fee, @NotNull String royalty_fee_collector, @NotNull long max_items, @NotNull long max_supply, @NotNull long max_supply_per_item, @NotNull String schema_name, @NotNull String[] authorized_minters, @NotNull String[] notify_accounts, @NotNull String[] authorized_mutable_data_editors, @NotNull ZSWAPIV1.KVPair[] metadata, @NotNull String external_metadata_url) {
        this.authorizer = authorizer;
        this.zsw_id = zsw_id;
        this.collection_id = collection_id;
        this.collection_type = collection_type;
        this.creator = creator;
        this.issuing_platform = issuing_platform;
        this.item_config = item_config;
        this.secondary_market_fee = secondary_market_fee;
        this.primary_market_fee = primary_market_fee;
        this.royalty_fee_collector = royalty_fee_collector;
        this.max_items = max_items;
        this.max_supply = max_supply;
        this.max_supply_per_item = max_supply_per_item;
        this.schema_name = schema_name;
        this.authorized_minters = authorized_minters;
        this.notify_accounts = notify_accounts;
        this.authorized_mutable_data_editors = authorized_mutable_data_editors;
        this.metadata = metadata;
        this.external_metadata_url = external_metadata_url;
    }
}

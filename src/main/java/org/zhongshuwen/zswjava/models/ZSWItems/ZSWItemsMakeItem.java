package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
/*


  {
    "account": "zsw.items",
    "name": "mkitem",
    "data": {
      "authorizer": "zsw.admin",
      "authorized_minter": "kxjdzzzz111b",
      "item_id": 291481,
      "zsw_id": "291481",
      "item_config": 11,
      "item_template_id": 1339,
      "max_supply": 9000000,
      "schema_name": "schematest12",
      "immutable_metadata": [
        { "key": "name", "value": ["string", "My first item"] },
        {
          "key": "image_url",
          "value": [
            "string",
            "https://cangpin.test.chao7.cn/f/images/shanghai.png"
          ]
        },
        { "key": "rarity", "value": ["uint32", 10] }
      ],
      "mutable_metadata": []
    }
  },
 */
public class ZSWItemsMakeItem {


    @SerializedName("authorizer")
    @NotNull
    public String authorizer;
    @SerializedName("authorized_minter")
    @NotNull
    public String authorized_minter;

    @SerializedName("item_id")
    @NotNull
    public String item_id;
    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;


    @SerializedName("item_config")
    @NotNull
    public int item_config;


    @SerializedName("item_template_id")
    @NotNull
    public String item_template_id;

    @SerializedName("max_supply")
    @NotNull
    public String max_supply;


    @SerializedName("schema_name")
    @NotNull
    public String schema_name;


    @SerializedName("immutable_metadata")
    @NotNull
    public ZSWItemsSchemaFieldValue[] immutable_metadata;
    @SerializedName("mutable_metadata")
    @NotNull
    public ZSWItemsSchemaFieldValue[] mutable_metadata;


    public ZSWItemsMakeItem(@NotNull String authorizer, @NotNull String authorized_minter, @NotNull String item_id, @NotNull String zsw_id, @NotNull int item_config, @NotNull String item_template_id, @NotNull String max_supply, @NotNull String schema_name, @NotNull ZSWItemsSchemaFieldValue[] immutable_metadata, @NotNull ZSWItemsSchemaFieldValue[] mutable_metadata) {
        this.authorizer = authorizer;
        this.authorized_minter = authorized_minter;
        this.item_id = item_id;
        this.zsw_id = zsw_id;
        this.item_config = item_config;
        this.item_template_id = item_template_id;
        this.max_supply = max_supply;
        this.schema_name = schema_name;
        this.immutable_metadata = immutable_metadata;
        this.mutable_metadata = mutable_metadata;
    }
}

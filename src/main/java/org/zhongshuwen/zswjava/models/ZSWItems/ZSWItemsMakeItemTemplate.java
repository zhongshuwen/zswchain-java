package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
public class ZSWItemsMakeItemTemplate implements Serializable{


    @SerializedName("authorizer")
    @NotNull
    public String authorizer;


    @SerializedName("creator")
    @NotNull
    public String creator;

    @SerializedName("zsw_id")
    @NotNull
    public String zsw_id;

    @SerializedName("item_template_id")
    @NotNull
    public String item_template_id;


    @SerializedName("collection_id")
    @NotNull
    public String collection_id;

    @SerializedName("item_type")
    @NotNull
    public int collection_type;




    @SerializedName("schema_name")
    @NotNull
    public String schema_name;
    @SerializedName("immutable_metadata")
    @NotNull
    public ZSWItemsSchemaFieldValue[] immutable_metadata;


    @SerializedName("item_external_metadata_url_template")
    @NotNull
    public String item_external_metadata_url_template;


    public ZSWItemsMakeItemTemplate(@NotNull String authorizer, @NotNull String creator, @NotNull String zsw_id, @NotNull String item_template_id, @NotNull String collection_id, @NotNull int collection_type, @NotNull String schema_name, @NotNull ZSWItemsSchemaFieldValue[] immutable_metadata, @NotNull String item_external_metadata_url_template) {
        this.authorizer = authorizer;
        this.creator = creator;
        this.zsw_id = zsw_id;
        this.item_template_id = item_template_id;
        this.collection_id = collection_id;
        this.collection_type = collection_type;
        this.schema_name = schema_name;
        this.immutable_metadata = immutable_metadata;
        this.item_external_metadata_url_template = item_external_metadata_url_template;
    }
}

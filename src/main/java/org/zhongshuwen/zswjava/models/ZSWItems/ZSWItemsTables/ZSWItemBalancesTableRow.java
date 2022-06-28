package org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTables;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/*

            {
               "name": "item_id",
               "type": "uint64"
            },
            {
               "name": "status",
               "type": "uint32"
            },
            {
               "name": "balance_normal_liquid",
               "type": "uint64"
            },
            {
               "name": "balance_frozen",
               "type": "uint64"
            },
            {
               "name": "balance_in_custody_liquid",
               "type": "uint64"
            },
            {
               "name": "active_custodian_pairs",
               "type": "uint64[]"
            }

 */
public class ZSWItemBalancesTableRow implements Serializable {

    @SerializedName("item_id")
    @NotNull
    public String item_id;

    @SerializedName("status")
    @NotNull
    public long status;


    @SerializedName("balance_normal_liquid")
    @NotNull
    public long balance_normal_liquid;
    @SerializedName("balance_frozen")
    @NotNull
    public long balance_frozen;

    @SerializedName("balance_in_custody_liquid")
    @NotNull
    public long balance_in_custody_liquid;


    @SerializedName("issuing_platform")
    @NotNull
    public String issuing_platform;

    @SerializedName("item_config")
    @NotNull
    public int item_config;

    @SerializedName("active_custodian_pairs")
    public String[] active_custodian_pairs;
}

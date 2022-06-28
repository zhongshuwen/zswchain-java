package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
/*
[
            {
               "name": "authorizer",
               "type": "name"
            },
            {
               "name": "from",
               "type": "name"
            },
            {
               "name": "to",
               "type": "name"
            },
            {
               "name": "from_custodian",
               "type": "name"
            },
            {
               "name": "to_custodian",
               "type": "name"
            },
            {
               "name": "freeze_time",
               "type": "uint32"
            },
            {
               "name": "use_liquid_backup",
               "type": "bool"
            },
            {
               "name": "max_unfreeze_iterations",
               "type": "uint32"
            },
            {
               "name": "item_ids",
               "type": "uint64[]"
            },
            {
               "name": "amounts",
               "type": "uint64[]"
            },
            {
               "name": "memo",
               "type": "string"
            }
         ]

 */
public class ZSWItemsTransfer implements Serializable {
    @SerializedName("authorizer")
    @NotNull
    public String authorizer;

    @SerializedName("from")
    @NotNull
    public String from;
    @SerializedName("from_custodian")
    @NotNull
    public String from_custodian;

    @SerializedName("to")
    @NotNull
    public String to;
    @SerializedName("to_custodian")
    @NotNull
    public String to_custodian;
    @SerializedName("freeze_time")
    @NotNull
    public long freeze_time;

    @SerializedName("use_liquid_backup")
    @NotNull
    public boolean use_liquid_backup;
    @SerializedName("max_unfreeze_iterations")
    @NotNull
    public int max_unfreeze_iterations;

    @SerializedName("item_ids")
    @NotNull
    public String[] item_ids;

    @SerializedName("amounts")
    @NotNull
    public String[] amounts;

    @SerializedName("memo")
    @NotNull
    public String memo;

    public ZSWItemsTransfer(@NotNull String authorizer, @NotNull String from, @NotNull String from_custodian, @NotNull String to, @NotNull String to_custodian, @NotNull long freeze_time, @NotNull boolean use_liquid_backup, @NotNull int max_unfreeze_iterations, @NotNull String[] item_ids, @NotNull String[] amounts, @NotNull String memo) {
        this.authorizer = authorizer;
        this.from = from;
        this.from_custodian = from_custodian;
        this.to = to;
        this.to_custodian = to_custodian;
        this.freeze_time = freeze_time;
        this.use_liquid_backup = use_liquid_backup;
        this.max_unfreeze_iterations = max_unfreeze_iterations;
        this.item_ids = item_ids;
        this.amounts = amounts;
        this.memo = memo;
    }
}

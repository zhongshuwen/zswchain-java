package org.zhongshuwen.zswjava.models.ZSWItems;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/*
[
            {
               "name": "minter",
               "type": "name"
            },
            {
               "name": "to",
               "type": "name"
            },
            {
               "name": "to_custodian",
               "type": "name"
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
            },
            {
               "name": "freeze_time",
               "type": "uint32"
            }
         ]

 */
public class ZSWItemsMint implements Serializable {

    @SerializedName("minter")
    @NotNull
    public String minter;

    @SerializedName("to")
    @NotNull
    public String to;

    @SerializedName("to_custodian")
    @NotNull
    public String to_custodian;

    @SerializedName("freeze_time")
    @NotNull
    public long freeze_time;


    @SerializedName("item_ids")
    @NotNull
    public String[] item_ids;

    @SerializedName("amounts")
    @NotNull
    public String[] amounts;

    @SerializedName("memo")
    @NotNull
    public String memo;

    public ZSWItemsMint(@NotNull String minter, @NotNull String to, @NotNull String to_custodian, @NotNull long freeze_time, @NotNull String[] item_ids, @NotNull String[] amounts, @NotNull String memo) {
        this.minter = minter;
        this.to = to;
        this.to_custodian = to_custodian;
        this.freeze_time = freeze_time;
        this.item_ids = item_ids;
        this.amounts = amounts;
        this.memo = memo;
    }
}

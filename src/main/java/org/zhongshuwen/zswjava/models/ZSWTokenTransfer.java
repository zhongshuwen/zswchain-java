package org.zhongshuwen.zswjava.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ZSWTokenTransfer implements Serializable {
    @SerializedName("from")
    @NotNull
    public String from;

    @SerializedName("to")
    @NotNull
    public String to;

    @SerializedName("quantity")
    @NotNull
    public String quantity;

    @SerializedName("memo")
    @NotNull
    public String memo;

}

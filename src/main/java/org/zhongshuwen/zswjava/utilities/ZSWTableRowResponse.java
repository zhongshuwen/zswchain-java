package org.zhongshuwen.zswjava.utilities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZSWTableRowResponse<T extends Serializable> implements Serializable {
    @SerializedName("rows")
    public T[] rows;

    @SerializedName("more")
    public boolean more;

    @SerializedName("next_key")
    public String next_key;



}

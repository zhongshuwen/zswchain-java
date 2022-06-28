package org.zhongshuwen.zswjava.abitypes;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class ZSWExtendedAsset implements Serializable {

    /**
     * The extension type
     */
    @SerializedName("quantity")
    @NotNull
    public String quantity;

    /**
     * The extension data
     */
    @SerializedName("contract")
    @NotNull
    public String contract;

    public ZSWExtendedAsset(String quantity, String contract){
        this.quantity = quantity;
        this.contract = contract;
    }

}

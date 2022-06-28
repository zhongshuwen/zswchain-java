package org.zhongshuwen.zswjava.abitypes;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class ZSWV1Extension implements Serializable {

    /**
     * The extension type
     */
    @SerializedName("type")
    @NotNull
    public int type;

    /**
     * The extension data
     */
    @SerializedName("data")
    @NotNull
    public byte[] data;

    public ZSWV1Extension(int type, byte[] data){
        this.type = type&0xffff;
        this.data = data;
    }

}

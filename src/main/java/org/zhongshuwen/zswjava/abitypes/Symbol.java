package org.zhongshuwen.zswjava.abitypes;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Symbol implements Serializable {

    /**
     * The symbol name
     */
    @SerializedName("name")
    @NotNull
    public String name;

    /**
     * The symbol precision
     */
    @SerializedName("precision")
    @NotNull
    public byte precision;
    public Symbol(String name, byte precision){
        this.name = name;
        this.precision = precision;
    }

}

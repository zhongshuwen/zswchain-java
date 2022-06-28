package org.zhongshuwen.zswjava.abitypes;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class PermissionLevel implements Serializable {

    /**
     * The extension type
     */
    @SerializedName("actor")
    @NotNull
    public String actor;

    /**
     * The extension data
     */
    @SerializedName("permission")
    @NotNull
    public String permission;

    public PermissionLevel(String actor, String permission){
        this.actor = actor;
        this.permission = permission;
    }

}

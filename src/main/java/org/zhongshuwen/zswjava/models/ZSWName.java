package org.zhongshuwen.zswjava.models;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Class holds block chain account name.
 */
public class ZSWName {

    /**
     * ZSWCHAIN account name in String format.
     */
    @NotNull private String accountName;

    /**
     * Initialize ZSWName object with ZSWCHAIN account name in String format
     *
     * @param accountName - input ZSWCHAIN account name in String format.
     */
    public ZSWName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get ZSWCHAIN account name in String format.
     *
     * @return ZSWCHAIN account name in String format.
     */
    @NotNull
    public String getAccountName() {
        return accountName;
    }

    /**
     * Get ZSWCHAIN account name
     *
     * @param accountName input ZSWCHAIN account name in string format.
     */
    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZSWName zswhqName = (ZSWName) o;
        return getAccountName().equals(zswhqName.getAccountName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountName());
    }
}

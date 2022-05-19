package org.zhongshuwen.zswjava.interfaces;

import java.util.List;
import java.util.Map;
import org.zhongshuwen.zswjava.error.abiProvider.GetAbiError;
import org.zhongshuwen.zswjava.models.ZSWName;
import org.jetbrains.annotations.NotNull;

/**
 * Interface of ABI Provider
 */
public interface IABIProvider {

    /**
     * Gets multiple ABI by list of ZSWName.
     * <br>
     * Check ABIProviderImpl.getABIs() flow in "complete workflow"
     * doc for more detail about the implementation
     *
     * @param chainId the chain id
     * @param accounts the accounts
     * @return the abis
     * @throws GetAbiError thrown if there are any exceptions during the getAbi process.
     */
    @NotNull
    Map<String, String> getAbis(@NotNull String chainId, @NotNull List<ZSWName> accounts) throws GetAbiError;

    /**
     * Gets abi by ZSWName.
     * <br>
     * Check ABIProviderImpl.getABI() flow in "complete workflow"
     * doc for more detail about the implementation
     *
     * @param chainId the chain id
     * @param account the account
     * @return the abi
     * @throws GetAbiError thrown if there are any exceptions during the getAbis process.
     */
    @NotNull
    String getAbi(@NotNull String chainId, @NotNull ZSWName account) throws GetAbiError;
}

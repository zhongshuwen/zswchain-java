package org.zhongshuwen.zswjava.interfaces;

import org.jetbrains.annotations.NotNull;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.error.rpcProvider.*;
import org.zhongshuwen.zswjava.models.rpcProvider.request.*;
import org.zhongshuwen.zswjava.models.rpcProvider.response.*;

public interface IRPCProviderExtended extends IRPCProvider{

    /**
     * Gets raw abi for a given contract.
     *
     * @param getAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     * @throws GetRawAbiRpcError thrown if there are any exceptions/backend error during the
     * getRawAbi() process.
     */
    @NotNull
    ZSWAPIV1.Abi getAbi(GetRawAbiRequest getAbiRequest) throws GetRawAbiRpcError;

}

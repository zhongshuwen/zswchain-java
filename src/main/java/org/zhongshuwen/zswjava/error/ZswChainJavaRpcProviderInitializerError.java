package org.zhongshuwen.zswjava.error;

import org.zhongshuwen.zswjava.error.rpcProvider.RpcProviderError;
import org.jetbrains.annotations.NotNull;

//
// Copyright © 2017-2019 block.one.
//

/**
 * Error thrown when there is an issue initializing the RPC Provider.
 */
public class ZswChainJavaRpcProviderInitializerError extends RpcProviderError {

    public ZswChainJavaRpcProviderInitializerError() {
    }

    public ZswChainJavaRpcProviderInitializerError(
            @NotNull String message) {
        super(message);
    }

    public ZswChainJavaRpcProviderInitializerError(
            @NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public ZswChainJavaRpcProviderInitializerError(
            @NotNull Exception exception) {
        super(exception);
    }

}

package org.zhongshuwen.zswjava.error.rpcProvider;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use any RPC call.
 * <br>
 *     Any exception class which is used in an RPC Provider should extend this Error class.
 */
public class RpcProviderError extends ZswChainError {

    public RpcProviderError() {
    }

    public RpcProviderError(@NotNull String message) {
        super(message);
    }

    public RpcProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public RpcProviderError(@NotNull Exception exception) {
        super(exception);
    }
}

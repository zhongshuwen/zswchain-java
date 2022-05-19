package org.zhongshuwen.zswjava.error.rpcProvider;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call, getRawAbi().
 */
public class GetRawAbiRpcError extends ZswChainError {

    public GetRawAbiRpcError() {
    }

    public GetRawAbiRpcError(@NotNull String message) {
        super(message);
    }

    public GetRawAbiRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetRawAbiRpcError(@NotNull Exception exception) {
        super(exception);
    }
}

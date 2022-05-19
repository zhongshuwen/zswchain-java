package org.zhongshuwen.zswjava.error.abiProvider;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method in an
 * AbiProvider implementation.
 */
public class AbiProviderError extends ZswChainError {

    public AbiProviderError() {
    }

    public AbiProviderError(@NotNull String message) {
        super(message);
    }

    public AbiProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public AbiProviderError(@NotNull Exception exception) {
        super(exception);
    }
}

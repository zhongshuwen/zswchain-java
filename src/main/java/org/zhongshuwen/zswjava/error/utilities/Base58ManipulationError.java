package org.zhongshuwen.zswjava.error.utilities;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error is thrown for exceptions that occur during Base58
 * encoding or decoding operations.
 */
public class Base58ManipulationError extends ZswChainError {
    public Base58ManipulationError() {
    }

    public Base58ManipulationError(@NotNull String message) {
        super(message);
    }

    public Base58ManipulationError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public Base58ManipulationError(@NotNull Exception exception) {
        super(exception);
    }
}

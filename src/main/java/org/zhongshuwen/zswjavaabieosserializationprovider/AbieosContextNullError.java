package org.zhongshuwen.zswjavaabieosserializationprovider;

import org.zhongshuwen.zswjava.error.serializationProvider.SerializationProviderError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception when the ABIEOS c++ code attempts to create its
 * working context during initialization.
 */

public class AbieosContextNullError extends SerializationProviderError {

    public AbieosContextNullError() {
    }

    public AbieosContextNullError(@NotNull String message) {
        super(message);
    }

    public AbieosContextNullError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public AbieosContextNullError(@NotNull Exception exception) {
        super(exception);
    }
}

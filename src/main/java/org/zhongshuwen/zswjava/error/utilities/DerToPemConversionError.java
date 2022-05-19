package org.zhongshuwen.zswjava.error.utilities;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error is thrown for exceptions involving conversions of keys
 * or signatures from DER encoded format to PEM.
 */
public class DerToPemConversionError extends ZswChainError {
    public DerToPemConversionError() {
    }

    public DerToPemConversionError(@NotNull String message) {
        super(message);
    }

    public DerToPemConversionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DerToPemConversionError(@NotNull Exception exception) {
        super(exception);
    }

}

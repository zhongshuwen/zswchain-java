

package org.zhongshuwen.zswjava.error.utilities;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method of ZSWFormatter
 */
public class ZSWFormatterError extends ZswChainError {

    public ZSWFormatterError() {
    }

    public ZSWFormatterError(@NotNull String message) {
        super(message);
    }

    public ZSWFormatterError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public ZSWFormatterError(@NotNull Exception exception) {
        super(exception);
    }
}

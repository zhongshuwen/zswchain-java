package org.zhongshuwen.zswjava.error.session;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method of TransactionProcessor
 */
public class TransactionProcessorError extends ZswChainError {

    public TransactionProcessorError() {
    }

    public TransactionProcessorError(@NotNull String message) {
        super(message);
    }

    public TransactionProcessorError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionProcessorError(@NotNull Exception exception) {
        super(exception);
    }
}

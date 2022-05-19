/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package org.zhongshuwen.zswjava.error.utilities;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.jetbrains.annotations.NotNull;

/**
 * Error that originates from the {@link org.zhongshuwen.zswjava.utilities.PEMProcessor} class.
 */
public class PEMProcessorError extends ZswChainError {

    public PEMProcessorError() {
    }

    public PEMProcessorError(@NotNull String message) {
        super(message);
    }

    public PEMProcessorError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public PEMProcessorError(@NotNull Exception exception) {
        super(exception);
    }
}

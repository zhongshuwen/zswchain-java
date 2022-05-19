/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package org.zhongshuwen.zswjava.error.utilities;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to convert a
 * signature to ZSW format and the signature is not canonical.
 * <br>
 *     * This exception only happens with signatures signed by a key generated by the SECP256K1
 *     algorithm.
 * <br>
 *     * The signature must be recreated and tested to pass this exception.
 */
public class ZSWFormatterSignatureIsNotCanonicalError extends ZSWFormatterError{
    public ZSWFormatterSignatureIsNotCanonicalError() {
    }

    public ZSWFormatterSignatureIsNotCanonicalError(@NotNull String message) {
        super(message);
    }

    public ZSWFormatterSignatureIsNotCanonicalError(@NotNull String message, @NotNull Exception exception) {
        super(message, exception);
    }

    public ZSWFormatterSignatureIsNotCanonicalError(@NotNull Exception exception) {
        super(exception);
    }
}
